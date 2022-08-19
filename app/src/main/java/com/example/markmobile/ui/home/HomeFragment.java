package com.example.markmobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;
import android.widget.Toast;

import com.example.markmobile.R;
import com.example.markmobile.databinding.FragmentHomeBinding;
import com.example.markmobile.entidades.Marcacion;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    //Declarar variables
    private TextView _tv_fechaHora, _tv_marcada;
    private EditText _ed_comentario;
    private Switch _sw_comentario;
    private String comentario = "no_comment";
    private Button _btn_registrar;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */

           return root; //Plantilla por Default
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //aca Programamos nosotros
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _tv_fechaHora = view.findViewById(R.id.tv_fechahora);
        _tv_marcada = view.findViewById(R.id.tv_marcada);
        _ed_comentario = view.findViewById(R.id.ed_comentario);
        _sw_comentario = view.findViewById(R.id.sw_comentario);
        _btn_registrar = view.findViewById(R.id.btn_registrar);

        _sw_comentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_sw_comentario.isChecked()){
                    //Quiere anadir comentario
                    _sw_comentario.setChecked(true);
                    _ed_comentario.setEnabled(true);

                }else{
                    //No Quiere anadir comentario
                    _sw_comentario.setChecked(false);
                    _ed_comentario.setEnabled(false);

                }
            }
        });

        _btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set La hora
                String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                _tv_fechaHora.setText(timeStamp);

                //Actualizando comentario
                actualizarComentario();

                //Creando Marcacion
                Marcacion marcacion = new Marcacion("SYSTEM",
                        _tv_marcada.getText().toString(),
                        _tv_fechaHora.getText().toString(),
                        comentario);

                //Se pasa el contexto
                marcacion.setContext(getActivity());

                //Validar cantidad marcaciones del dia.
                int cantidad_marcaciones_del_dia = marcacion.sp_cantidad_marcaciones_del_dia(timeStamp);
                if (cantidad_marcaciones_del_dia>3){
                    Toast.makeText(getContext(), "Marcaciones del dia realizadas: " + cantidad_marcaciones_del_dia, Toast.LENGTH_SHORT).show();
               }else{
                    //Se actuliza momento del dia
                     marcacion.setMarcada(momento_del_dia(cantidad_marcaciones_del_dia));
                    //Se inserta marcacion
                    if(marcacion.sp_insert()) {
                        Toast.makeText(getContext(), "Marcada agregada", Toast.LENGTH_SHORT).show();
                        _ed_comentario.setText(""); //Actualizando vista
                    }else{
                        Toast.makeText(getContext(),"Error: HomeFragment-Registrar", Toast.LENGTH_SHORT).show();
                    }
                };


            }
        });

        //Ultimo marcada en la base de datos.
        ultimaMarcada();
    }

    //FUNCIONES
    private String momento_del_dia(int i){
        String resultado = "";
        switch(i){
            case 0: resultado = "ENTRADA"; break;
            case 1: resultado = "E_ALMUERZO"; break;
            case 2: resultado = "S_ALMUERZO"; break;
            case 3: resultado = "SALIDA"; break;
        }
        return  resultado;
    }

    private void actualizarComentario(){
        String s = _ed_comentario.getText().toString();
        if (s.equals("")){
            comentario = "no_comment";
        }else{
            comentario = s;
        };
    }

    private void ultimaMarcada(){
        Marcacion marcacion = new Marcacion();
        marcacion.setContext(getActivity());

       marcacion = marcacion.sp_ultima_marcacion();

       _tv_marcada.setText(marcacion.getMarcada());
       _tv_fechaHora.setText(marcacion.getFechahora());

    }
}