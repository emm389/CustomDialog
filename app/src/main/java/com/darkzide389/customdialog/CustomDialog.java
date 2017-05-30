package com.darkzide389.customdialog;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.darkzide389.customdialog.adapter.AreasAdapter;
import com.darkzide389.customdialog.model.Area;

import java.util.ArrayList;
import java.util.List;

public class CustomDialog extends AppCompatActivity {

    // PREPARAMOS DOS ARREGLOS, UNO DONDE TENDRA TODAS LAS AREAS
    // Y EL OTRO PARA LAS AREAS SELECCIONADAS
    private List<Area> listaAreas;
    private List<Area> listaAreasSeleccionadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);

        final TextView areasEmpleo = (TextView)findViewById(R.id.areasEmpleo);
        // CLICKLISTENER PARA EL TEXTVIEW QUE ABRIRA NUESTRO DIALOG
        areasEmpleo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                areasEmpleo.setText("¿Cuales son las áreas de trabajo?");

                // INICIALIZAMOS NUESTROS ARREGLOS
                listaAreas = new ArrayList<>();
                listaAreasSeleccionadas = new ArrayList<>();

                // HACEMOS UN OBJETO DE TIPO DIALOG
                final Dialog dialog = new Dialog(CustomDialog.this);

                // INFLAMOS NUESTRO XML QUE CONTENDRA EL DIALOG
                View viewAreas = getLayoutInflater().inflate(R.layout.lista_areas, null, false);

                // OBTENEMOS LA REFERENCIA DEL LISTVIEW QUE ESTA EN EL XML DEL DIALOG
                final ListView listAreas = (ListView) viewAreas.findViewById(R.id.listAreas);

                // AQUI ES DONDE LLENAMOS NUESTRAS AREAS DESDE EL WEBSERVICE (METODO MAS ABAJO)
                listaAreas = getAreas();

                // HACEMOS UN OBJETO DE NUESTRO ADAPTADOR Y LE PASAMOS LOS PARAMETROS CORRESPONDIENTES
                // (CONTEXTO DE LA ACTIVITY, ARREGLO DE TODAS LAS AREAS, ARREGLO INICIALIZADO DE AREAS SELECCIONADAS)
                final AreasAdapter adapter = new AreasAdapter(CustomDialog.this, listaAreas, new ArrayList<Area>());

                // LE PASAMOS EL ADAPTADOR AL LISTVIEW
                listAreas.setAdapter(adapter);

                // AQUI CONFIGURAMOS EL DIALOG, DICIENDOLE QUE NO QUEREMOS QUE LE PONGA TITULO
                // YA QUE NOSOTROS LE PUSIMOS UNO, ADEMAS LE PASAMOS NUESTRO VIEW QUE INFLAMOS
                // MAS ARRIBA, DESPUES LE DECIMOS QUE LO MUESTRE CON .SHOW();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(viewAreas);
                dialog.show();

                // OBTENEMOS LAS REFERENCIAS DE LOS BOTONES ACEPTAR Y CANCELAR
                TextView btnAceptar = (TextView) viewAreas.findViewById(R.id.btnAceptar);
                TextView btnCancelar = (TextView) viewAreas.findViewById(R.id.btnCancelar);

                // CLICKLISTENER PARA CERRAR EL DIALOG
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                // CLICKLISTENER PARA OBTENER LAS AREAS SELECCIONADAS
                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // AQUI RECUPERAMOS LAS AREAS SELECCIONADAS DEL ADAPTADOR
                        // YA CUANDO ENVIES TODOS LOS DATOS DE TU ACTIVITY AL SERVIDOR
                        // TU ARREGLO DE AREAS SELECCIONADAS DEBERIA ESTAR LLENO (PONER VALIDACION)
                        // if (listaAreasSeleccionadas.size() > 0){
                        //      enviar
                        // } else {
                        //      mostrar mensaje al usuario que debe elegir areas
                        // }
                        listaAreasSeleccionadas = adapter.getAreas();

                        // PONEMOS EL TEXTO DE QUE YA HAY AREAS A NUESTRO TEXTVIEW PRINCIPAL
                        areasEmpleo.setText("Áreas seleccionadas");

                        // CERRAMOS EL DIALOG
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     * En este método haras el webservice para traerte las áreas
     */

    private List<Area> getAreas(){
        List<Area> lista = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Area area = new Area();
            area.setId(i + 1);

            switch (i){
                case 0:
                    area.setNombre("Administrativo");
                    break;
                case 1:
                    area.setNombre("Producción");
                    break;
                case 2:
                    area.setNombre("Ingeniería");
                    break;
                case 3:
                    area.setNombre("Contable");
                    break;
                case 4:
                    area.setNombre("Calidad");
                    break;
            }

            lista.add(area);
        }
        return lista;
    }
}
