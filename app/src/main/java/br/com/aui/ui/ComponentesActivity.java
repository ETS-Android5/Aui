package br.com.aui.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import br.com.aui.R;
import br.com.componentes.EditTexCurrency;
import br.com.componentes.EditTextSearch;
import br.com.componentes.EditTextTitle;
import br.com.componentes.TextViewTitle;
import br.com.componentes.Util.Constantes;


public class ComponentesActivity extends AppCompatActivity {

    EditTextTitle multilineEditTextLegenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        final EditTextTitle senhaEditTextLegenda = findViewById(R.id.senhaEditTextLegenda);
        senhaEditTextLegenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senhaEditTextLegenda.mostraSenha();
            }
        });

        EditTextTitle editTextLegendaSelection = findViewById(R.id.editTextLegendaSelection);
        editTextLegendaSelection.setSelection(editTextLegendaSelection.getString().length() - 2);

        final EditTexCurrency editTexCurrencyLegenda = findViewById(R.id.editTexFinaceirotLegenda);

        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double aqui = editTexCurrencyLegenda.getDouble();

                System.out.println("" + editTexCurrencyLegenda.getDouble());
            }
        });


        EditTextSearch searchLegenda = findViewById(R.id.searchLegenda);
        searchLegenda.setInputTypeSearch(Constantes.textPassword);

        TextViewTitle textViewTitle = findViewById(R.id.textViewsd);
        textViewTitle.setFont("fonts/Lobster-Regular.ttf");

       TextViewTitle textViewTitle1 = findViewById(R.id.textViewsdasd);
       textViewTitle1.setDescricao(Html.fromHtml("<b>Aureo</> Jose", Html.FROM_HTML_MODE_COMPACT));

    }
}
