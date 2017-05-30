package com.darkzide389.customdialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.darkzide389.customdialog.R;
import com.darkzide389.customdialog.model.Area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Emmanuel.
 */

public class AreasAdapter extends BaseAdapter {

    private Context context;
    private List<Area> listaAreas;
    private List<Area> listaAreasSeleccionadas;
    private LayoutInflater inflater;
    private boolean[] flags;

    public AreasAdapter(Context context, List<Area> listaAreas, List<Area> listaAreasSeleccionadas){
        this.context = context;
        this.listaAreas = listaAreas;
        inflater = LayoutInflater.from(context);
        this.listaAreasSeleccionadas = listaAreasSeleccionadas;

        // ESTE ARREGLO DE BOOLEANOS ES PARA CONTROLAR LOS ELEMENTOS SELECCIONADOS
        // Y A LA VEZ SOLUCIONAR UN BUG DEL LISTADAPTER
        flags = new boolean[listaAreas.size()];

        // AQUI LLENAMOS EL ARREGLO CON PUROS FALSE, DESPUES CON EL ARREGLO DE AREAS SELECCIONADAS
        // LE IREMOS PONIENDO TRUE SEGUN LOS ELEMENTOS SELECCIONADOS
        for (int i = 0; i < flags.length; i++){
            flags[i] = false;
        }
    }

    @Override
    public int getCount() {
        return listaAreas.size();
    }

    @Override
    public Area getItem(int i) {
        return listaAreas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaAreas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AreasHolder holder;
        if (view == null){
            holder = new AreasHolder();
            view = inflater.inflate(R.layout.item_area, viewGroup, false);

            holder.nombre = (TextView) view.findViewById(R.id.nombre);
            holder.palomita = (CheckBox) view.findViewById(R.id.palomita);

            view.setTag(holder);
        } else {
            holder = (AreasHolder)view.getTag();
        }

        holder.nombre.setText(listaAreas.get(i).getNombre());

        // le pasamos la posicion como tag para poder accesar a la misma mas abajo
        holder.palomita.setTag(i);

        // BLOQUE PARA SABER SI HAY AREAS SELECCIONADAS (ESTO ES CUANDO YA SE HABIAN SELECCIONADO AREAS PREVIAMENTE)
        // SI LAS HAY PONEMOS EL CHECK SELECCIONADO, SI NO PS NO JAJA
        if (listaAreasSeleccionadas.size() > 0){
            for (int j = 0; j < listaAreasSeleccionadas.size(); j++) {
                if (listaAreas.get(i).getId() == listaAreasSeleccionadas.get(j).getId()) {
                    if (!listaAreasSeleccionadas.contains(listaAreas.get(i))) {
                        holder.palomita.setChecked(true);
                        flags[i] = true;
                        listaAreasSeleccionadas.add(listaAreas.get(i));
                    }
                } else {
                    holder.palomita.setChecked(flags[i]);
                }
            }
        } else {
            holder.palomita.setChecked(flags[i]);
        }

        // BLOQUE DEL LISTENER PARA SABER CUANDO UN ELEMENTO HA SIDO SELECCIONADO Y ASI PONERLO EN EL
        // ARREGLO DE AREAS SELECCIONADAS, CUANDO UN ELEMENTO ESTABA SELECCIONADO Y SE DECIDIO QUITAR
        // ENTONCES LO REMOVEMOS DEL ARREGLO DE AREAS SELECCIONADAS
        holder.palomita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int pos = (int)compoundButton.getTag();
                flags[pos] = b;
                if (b){
                    if (!listaAreasSeleccionadas.contains(listaAreas.get(pos))) {
                        listaAreasSeleccionadas.add(listaAreas.get(pos));
                    }
                } else {
                    if (listaAreasSeleccionadas.contains(listaAreas.get(pos))) {
                        if (listaAreasSeleccionadas.size() == 1) {
                            listaAreasSeleccionadas = new ArrayList<>();
                        } else {
                            listaAreasSeleccionadas.remove(listaAreas.get(pos));
                        }
                    }
                }
            }
        });

        return view;
    }

    // ESTE METODO NOS AYUDARA A RECUPERAR AQUELLOS ELEMENTOS QUE HAN SIDO SELECCIONADOS
    // PARA SU POSTERIOR ENVIO A LA BASE DE DATOS
    public List<Area> getAreas(){
        return listaAreasSeleccionadas;
    }

    private class AreasHolder{
        private TextView nombre;
        private CheckBox palomita;
    }
}
