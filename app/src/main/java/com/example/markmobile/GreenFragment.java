package com.example.markmobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


import com.example.markmobile.mydatabase.MyBD;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GreenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GreenFragment newInstance(String param1, String param2) {
        GreenFragment fragment = new GreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_green, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println(timeStamp);

        TextView txt_Date = view.findViewById(R.id.txt_date);
        Button btn_Date = view.findViewById(R.id.btn_date);
        Button btn_Prueba = view.findViewById(R.id.btn_prueba);

        CheckBox checkBoxComentario = view.findViewById(R.id.check_comentario);
        EditText editTextComentario = view.findViewById(R.id.editText_comentario);



        btn_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_Date.setText("La fecha es: " + timeStamp);
                System.out.println(timeStamp);

            }

        });

        btn_Prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                //Fecha actual
                Calendar calendar = Calendar.getInstance();
                System.out.println("Fecha Actual:" + sdf.format(calendar.getTime()));

                //A la fecha actual le pongo el día 1
                calendar.set(Calendar.DAY_OF_MONTH,1);
                System.out.println("Primer día del mes actual:" + sdf.format(calendar.getTime()));

                //Se le agrega 1 mes
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
                System.out.println("Fecha del siguiente mes:" + sdf.format(calendar.getTime()));
                System.out.println("1-Último día del mes siguiente " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

                //Se le quita 1 mes
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-2);//le quito 2 meses porque ya le había sumado 1 mes
                System.out.println("Fecha del primer día del mes anterior: " + sdf.format(calendar.getTime()));
                System.out.println("2.- Primer día del mes anterior: " + calendar.getActualMinimum(Calendar.DAY_OF_MONTH));


                //Pruebasssss
                System.out.println("-");
                System.out.println("Pruebas");
                calendar.set(2019,1,14);
                System.out.println("Ultimo dia del mes: " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));


            }

        });

        checkBoxComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxComentario.isChecked() ) {
                    editTextComentario.setEnabled(true);
                }else{
                    editTextComentario.setEnabled(false);
                };

            }
        });


    }

}