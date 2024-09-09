package com.example.calculadorainercia;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextRaio, editTextMassa, editTextAltura;
    private TextView textViewResultado, textViewAdditionalInfo;
    private Button botaoCalcular;
    private Spinner spinnerTipoObjeto, spinnerEixoRotacao;
    private CheckBox checkBoxEhSolido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupSpinners();
        setupButton();
    }

    private void initViews() {
        editTextRaio = findViewById(R.id.editTextRaio);
        editTextMassa = findViewById(R.id.editTextMassa);
        editTextAltura = findViewById(R.id.editTextAltura);
        textViewResultado = findViewById(R.id.textViewResultado);
        textViewAdditionalInfo = findViewById(R.id.textViewAdditionalInfo);
        botaoCalcular = findViewById(R.id.botaoCalcular);
        spinnerTipoObjeto = findViewById(R.id.spinnerTipoObjeto);
        spinnerEixoRotacao = findViewById(R.id.spinnerEixoRotacao);
        checkBoxEhSolido = findViewById(R.id.checkBoxEhSolido);
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> adapterTipoObjeto = ArrayAdapter.createFromResource(this,
                R.array.tipos_objeto, android.R.layout.simple_spinner_item);
        adapterTipoObjeto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoObjeto.setAdapter(adapterTipoObjeto);

        ArrayAdapter<CharSequence> adapterEixoRotacao = ArrayAdapter.createFromResource(this,
                R.array.eixos_rotacao, android.R.layout.simple_spinner_item);
        adapterEixoRotacao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEixoRotacao.setAdapter(adapterEixoRotacao);

        spinnerTipoObjeto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Cilindro")) {
                    spinnerEixoRotacao.setVisibility(View.VISIBLE);
                    checkBoxEhSolido.setVisibility(View.VISIBLE);
                    editTextAltura.setVisibility(View.VISIBLE); // Sempre visível para cilindro
                } else {
                    spinnerEixoRotacao.setVisibility(View.GONE);
                    editTextAltura.setVisibility(View.GONE);
                    checkBoxEhSolido.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerEixoRotacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupButton() {
        botaoCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateInerciaEVolume();
            }
        });
    }

    private void calculateInerciaEVolume() {
        try {
            double raio = Double.parseDouble(editTextRaio.getText().toString());
            double massa = Double.parseDouble(editTextMassa.getText().toString());
            double altura = 0.0;
            if (editTextAltura.getVisibility() == View.VISIBLE) {
                altura = Double.parseDouble(editTextAltura.getText().toString());
            }
            boolean ehSolido = checkBoxEhSolido.isChecked();
            String tipoObjeto = spinnerTipoObjeto.getSelectedItem().toString();
            String eixoRotacao = spinnerEixoRotacao.getSelectedItem().toString();

            double volume = CalcularInercia.calcularVolume(tipoObjeto, raio, altura);
            double inercia = CalcularInercia.calcularInercia(raio, altura, massa, ehSolido, tipoObjeto, eixoRotacao);
            double densidade = 0;
            if (volume > 0) { // Garantir que não dividimos por zero
                densidade = massa / volume;
            } else {
                textViewAdditionalInfo.setText("Erro: Volume calculado é zero!");
                return;
            }

            textViewResultado.setText(String.format("Momento de Inércia: %.2f kg·m²", inercia));
            textViewAdditionalInfo.setText(String.format("Volume: %.2f m³, Densidade: %.2f kg/m³", volume, densidade));
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Por favor, insira valores válidos em todos os campos necessários.", Toast.LENGTH_SHORT).show();
        }
    }
}
