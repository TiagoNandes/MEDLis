package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medlis.nfcRecords.NdefMessageParser;
import com.example.medlis.nfcRecords.ParsedNdefRecord;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LerEtiqueta extends AppCompatActivity {
    private static final String TAG = "TAG";
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler_etiqueta);
        final ConstraintLayout menu = findViewById(R.id.header);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(LerEtiqueta.this, menu.class);
                startActivity(q1);
            }
        });

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NO NFC", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
        }
        //pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        //nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);

        Log.e("S", "NOT"+new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        /* super.onResume();
        if (nfcAdapter != null) {
           if (!nfcAdapter.isEnabled()) {
                showWirelessSettings();
                nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
            }
        }*/
    }

    @Override
    protected void onPause() {
        /*super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }*/
        super.onPause();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
        Log.e("NEwS", "NOT WORKINGLOOOLL");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        /*super.onNewIntent(intent);
        setIntent(intent);
        resolveIntent(intent);*/
        super.onNewIntent(intent);
        //Log.e("NES", "NOT WORKINGLOOOLL");
        resolveIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            resolveIntent(intent);
            Log.e("NES", "NOT WORKINGLOOOLL");
            // drop NFC events
        }
    }

    private void resolveIntent(Intent intent) {
        Log.e("NasS", "Entrou");
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {


            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];


                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
                Log.e("NasS", "Entrou +"+msgs);
            } else {
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                byte[] payload = dumpTagData(tag).getBytes();
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
                NdefMessage msg = new NdefMessage(new NdefRecord[]{record});
                msgs = new NdefMessage[]{msg};
                /*Intent q1 = new Intent(LerEtiqueta.this, EtiquetaLida.class);
                startActivity(q1);*/
                //14:46
                Log.e("NasS", "Entrou +++"+msgs);
            }
            displayMsgs(msgs);
        }
    }

    private void displayMsgs(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        List<ParsedNdefRecord> records = NdefMessageParser.parse(msgs[0]);
        final int size = records.size();
        for (int i = 0; i < size; i++) {
            ParsedNdefRecord record = records.get(i);
            String str = record.str();
            builder.append(str).append("\n");
        }
        final TextView text = findViewById(R.id.textView9);
        //Log.e("NA", "Display +++"+builder.toString());

        //text.setText(builder.toString());
        writeOnDatabase(builder.toString());

        Intent q1 = new Intent(LerEtiqueta.this, EtiquetaLida.class);

        startActivity(q1);
    }

    private void showWirelessSettings() {
        Toast.makeText(this, "Precisa de ativar o NFC", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }

    private String dumpTagData(Tag tag) {
        StringBuilder sb = new StringBuilder();
        byte[] id = tag.getId();
        sb.append("ID(hex): ").append(toHex(id)).append("\n");
        sb.append("ID (reverser hex): ").append(toReversedHex(id)).append("\n");
        sb.append("ID (dec): ").append(toDec(id)).append("\n");
        sb.append("ID (reversed dec): ").append(toReversedDec(id)).append('\n');

        String prefix = "android.nfc.tech";
        sb.append("Technologies: ");
        for (String tech : tag.getTechList()) {
            sb.append(tech.substring(prefix.length()));
            sb.append(", ");

        }
        sb.delete(sb.length() - 2, sb.length());
        for (String tech : tag.getTechList()) {
            if (tech.equals(MifareClassic.class.getName())) {
                sb.append("\n");
                String type = "Unknown";
                try {
                    MifareClassic mifareTag = MifareClassic.get(tag);
                    switch (mifareTag.getType()) {
                        case MifareClassic.TYPE_CLASSIC:
                            type = "Classic";
                            break;
                        case MifareClassic.TYPE_PLUS:
                            type = "Plus";
                            break;
                        case MifareClassic.TYPE_PRO:
                            type = "Pro";
                            break;
                    }
                    sb.append("Mifare Classic Type: ");
                    sb.append(type);
                    sb.append('\n');

                    sb.append("Mifare Size: ");
                    sb.append(mifareTag.getSize() + "bytes");
                    sb.append('\n');

                    sb.append("Mifare Sectors: ");
                    sb.append(mifareTag.getSectorCount());
                    sb.append('\n');

                    sb.append("Mifare blocks: ");
                    sb.append(mifareTag.getBlockCount());
                    sb.append('\n');
                } catch (Exception e) {
                    sb.append("Mifare classic error: " + e.getMessage());
                }
            }
            if (tech.equals(MifareUltralight.class.getName())) {
                sb.append('\n');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()) {
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        type = "UltraLight";
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:
                        type = "Ultralight C";
                        break;
                }
                sb.append("Mifare Ultralight type: ");
                sb.append(type);
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(b));

            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private String toReversedHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            int b = bytes[i] & 0xff;
            if (b < 0x10) {
                sb.append(Integer.toHexString(b));
            }
        }
        return sb.toString();
    }

    private long toDec(byte[] bytes) {
        long result = 0;
        long factor = 0;
        for (int i = 0; i < bytes.length; i++) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    private long toReversedDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }
    private void writeOnDatabase(String text){
        //TODO Falta
        String[] arrOfStr = text.split(";");
        for (String a : arrOfStr)
           // Log.d(TAG, "arr"+a);
        Log.d(TAG, "arr"+arrOfStr.length);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String dosage_description =arrOfStr[0];
        String dosage_hours =arrOfStr[1];
        String expiry_date =arrOfStr[2];
        String id_medicine =arrOfStr[3];
        Integer remaining_quantity = 0;
        Object neww = text;
        Map<String, Object> User_med = new HashMap<>();
        User_med.put("dosage_description", dosage_description);
        User_med.put("dosage_hours", dosage_hours);
        User_med.put("expiry_date", expiry_date);
        User_med.put("id_medicine", id_medicine);
        User_med.put("remaining_quantity", remaining_quantity);//igual get med, campo boxQuantity


        fStore.collection("Users").document(user.getUid()).collection("User_med").add(User_med);

        /*Users.put("dosage_description", dosage_description);
        Users.put("dosage_hours", dosage_hours);
        Users.put("expiry_date", expiry_date);
        Users.put("id_medicine", id_medicine);
        Users.put("remaining_quantity", remaining_quantity);
        documentReference.set(Users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "user profile is created for !!!!"+ user.getUid());
            }
        });*/
        // Sign in success, update UI with the signed-in user's information
        Log.d(TAG, "createUserWithEmail:success"+ user.getUid());
    }

}
