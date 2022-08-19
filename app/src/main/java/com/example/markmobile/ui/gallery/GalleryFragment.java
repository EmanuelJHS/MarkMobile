package com.example.markmobile.ui.gallery;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.widget.Toast;

import com.example.markmobile.R;
import com.example.markmobile.databinding.FragmentGalleryBinding;
import com.example.markmobile.entidades.Conexion;
import com.example.markmobile.entidades.Marcacion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    //Declaracion de variables
    private TableLayout _table_consulta;
    private ArrayList<VistaTabla> arrayVistaTabla;
    private VistaTabla vistaTabla;
    private Button btnConsultar;
    private Spinner spinnerYear, spinnerMonth;
    private Context theContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Aca programamos nosotros
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        theContext = getActivity();
        //Enlazando variables
        _table_consulta = view.findViewById(R.id.table_consulta);
        CreandoTabla();

        //Marcaciones del mes actual
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        int year = Integer.parseInt(timeStamp.substring(0,4));
        int month = Integer.parseInt(timeStamp.substring(5,7));
        setData(year, month);

        spinnerYear = view.findViewById(R.id.spinner_year);
        spinnerMonth = view.findViewById(R.id.spinner_month);
        setSpinnerYear(year, month);  //Se llenan los spinners por primera vez, con la fecha actual.

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // System.out.println("Seleccionan: "+ spinnerYear.getSelectedItem().toString());
                setSpinnerMonth(Integer.parseInt(spinnerYear.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnConsultar = view.findViewById(R.id.btn_consultar);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Limpiar tabla
                LimpiarTabla();
                //SetData(yyyy,MM)
                setData(Integer.parseInt(spinnerYear.getSelectedItem().toString()),
                        Integer.parseInt(spinnerMonth.getSelectedItem().toString()));
            }
        });

    }

    private void CreandoTabla(){
        Context theContext = getActivity();


        TableRow tbrow0 = new TableRow(theContext); //Rows for headers

        //Table Headers
        //columm 0
        TextView tv0 = new TextView(theContext);
        tv0.setText("D");
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.CENTER);
        tbrow0.addView(tv0);

        //columm 1
        TextView tv1 = new TextView(theContext);
        tv1.setText("E");
        tv1.setTextColor(Color.BLACK);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);

        //columm 2
        TextView tv2 = new TextView(theContext);
        tv2.setText("EA");
        tv2.setTextColor(Color.BLACK);
        tv2.setGravity(Gravity.CENTER);
        tbrow0.addView(tv2);

        //columm 3
        TextView tv3 = new TextView(theContext);
        tv3.setText("SA");
        tv3.setTextColor(Color.BLACK);
        tv3.setGravity(Gravity.CENTER);
        tbrow0.addView(tv3);

        //columm 4
        TextView tv4 = new TextView(theContext);
        tv4.setText("S");
        tv4.setTextColor(Color.BLACK);
        tv4.setGravity(Gravity.CENTER);
        tbrow0.addView(tv4);

        //End Table Headers
        _table_consulta.addView(tbrow0); //Add to TableLayout

        //Below is the table data with 4 columns
        //rows
        //LLenando tabla en blanco y agregando al arraylist:
        arrayVistaTabla = new ArrayList<>(); //Un nuevo objeto
        for (int i = 0; i<32; i++) {

            TableRow tbrow = new TableRow(theContext); //Table row for new data
            vistaTabla = new VistaTabla(); //Un nuevo objeto

            //if(i%2==0) tbrow.setBackgroundColor(Color.parseColor("#c4edf5")); //Pintando las celdas
            //Para SetMargins
            android.widget.TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(3, 3, 3, 3);

            //columns
            for (int j = 0; j<5; j++){
                TextView tv = new TextView(theContext); //Un nuevo objeto
                tv.setTextColor(Color.BLACK);
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundColor(Color.parseColor("#ffffff"));
                tv.setLayoutParams(layoutParams); //SetMargins
                //tv.setText(""); //Day Entrada, Almuerzo y Salida.

                LlenandoVistaTabla(j,tv); //Para Guardar los TextView y poder manipularlos.
                tbrow.addView(tv);
            }

            arrayVistaTabla.add(vistaTabla); //Agregandolo al ArrayList de los objetos.
            arrayVistaTabla.get(i).getDay().setText(String.valueOf(i)); //Dia del mes
            _table_consulta.addView(tbrow); //Add to TableLayout
        }
        //Poner invisible la fila 0
        arrayVistaTabla.get(0).getDay().setVisibility(getView().GONE);
        arrayVistaTabla.get(0).getEntrada().setVisibility(getView().GONE);
        arrayVistaTabla.get(0).getE_almuerzo().setVisibility(getView().GONE);
        arrayVistaTabla.get(0).getS_almuerzo().setVisibility(getView().GONE);
        arrayVistaTabla.get(0).getSalida().setVisibility(getView().GONE);


    }

    private void LimpiarTabla(){
        for (int i = 0; i< arrayVistaTabla.size(); i++){
            arrayVistaTabla.get(i).getEntrada().setText("");
            arrayVistaTabla.get(i).getE_almuerzo().setText("");
            arrayVistaTabla.get(i).getS_almuerzo().setText("");
            arrayVistaTabla.get(i).getSalida().setText("");
        }
    }

    //Metodo que almacena los textview en un arraylist
    private void LlenandoVistaTabla(int y, TextView tv){
    switch(y) {
        case 0: vistaTabla.setDay(tv); break;
        case 1: vistaTabla.setEntrada(tv); break;
        case 2: vistaTabla.setE_almuerzo(tv); break;
        case 3: vistaTabla.setS_almuerzo(tv); break;
        case 4: vistaTabla.setSalida(tv); break;
    }

    }

    private void Impriendo_tabla(){
        System.out.println("Impriendo tabla");
        String day, e, ea, sa, s;
        for(int i = 0; i < arrayVistaTabla.size(); i++){
          day = arrayVistaTabla.get(i).getDay().getText().toString();
          e = arrayVistaTabla.get(i).getEntrada().getText().toString();
          ea = arrayVistaTabla.get(i).getE_almuerzo().getText().toString();
          sa = arrayVistaTabla.get(i).getS_almuerzo().getText().toString();
          s = arrayVistaTabla.get(i).getSalida().getText().toString();
          System.out.println("| "+day+" | "+e+" | "+ea+" | "+sa+" | "+s+" | "+i+" | "); //Impresion de Prueba
        }
    }

    private void setData(int y, int m){
        //System.out.println("SET DATA");
        Marcacion marcacion = new Marcacion();
        marcacion.setContext(getActivity()); //Pasamos el contexto

        ArrayList<Marcacion> aMarcacion = new ArrayList<>();
        aMarcacion = marcacion.sp_Marcaciones_get(y,m); //Trae un ArrayList<Marcacion>

        for(int i = 0; i < aMarcacion.size(); i++){
            //Primero Obtener el dia, donde se realizaron marcaciones
           String fechahora = aMarcacion.get(i).getFechahora(); // FECHA Y HORA
            //System.out.println("Fecha hora: "+ fechahora + ", sub: "+ (fechahora.substring(11)));
           int dia = Integer.parseInt(fechahora.substring(8,10)); //Del Caracter 8 al 10;
           String hora = fechahora.substring(11,16);
           String marcada = aMarcacion.get(i).getMarcada(); //entrada, almuerzo, salida

            //LLenando la tabla
            switch (marcada){
                case "ENTRADA": arrayVistaTabla.get(dia).getEntrada().setText(hora);break;
                case "E_ALMUERZO": arrayVistaTabla.get(dia).getE_almuerzo().setText(hora);break;
                case "S_ALMUERZO": arrayVistaTabla.get(dia).getS_almuerzo().setText(hora);break;
                case "SALIDA": arrayVistaTabla.get(dia).getSalida().setText(hora);break;
            }
            //System.out.println("Dia: " + dia +", Marcada: "+ marcada +", Hora: " + hora ); //Para los comentarios, pero en RecycleView
        }
    }

    private void setSpinnerYear(int year, int month){
        //Se Ejecuta una vez
            Conexion conexion = new Conexion();
            conexion.setContext(theContext);

            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList = conexion.sp_getYears();

            for (Integer i : arrayList)
                System.out.println(i);

            ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(theContext, android.R.layout.simple_spinner_item, arrayList);
            spinnerYear.setAdapter(adapterYear);
            spinnerYear.setSelection(arrayList.indexOf(year));
            System.out.println("year: " + year);
            System.out.println("index: " + arrayList.indexOf(year));

            arrayList = conexion.sp_getMonths(year);
            ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(theContext, android.R.layout.simple_spinner_item, arrayList);
            spinnerMonth.setAdapter(adapterMonth);
            spinnerMonth.setSelection(arrayList.indexOf(month));

    }

    private void setSpinnerMonth (int year){
        //Se ejecutara dependiendo del año seleccionado
        Conexion conexion = new Conexion();
        conexion.setContext(theContext);

        ArrayList<Integer>  arrayList = new ArrayList<>();
        arrayList = conexion.sp_getMonths(year);

        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(theContext, android.R.layout.simple_spinner_item, arrayList);
        spinnerMonth.setAdapter(adapterYear);
    }


}