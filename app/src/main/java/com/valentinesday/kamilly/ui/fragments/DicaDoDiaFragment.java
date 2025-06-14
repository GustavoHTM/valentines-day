package com.valentinesday.kamilly.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.valentinesday.kamilly.R;
import com.valentinesday.kamilly.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DicaDoDiaFragment extends Fragment {
    
    private ImageView imageView;
    private TextView textDica;
    private List<DicaItem> dicas;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dica_do_dia, container, false);
        
        imageView = view.findViewById(R.id.image_dica);
        textDica = view.findViewById(R.id.text_dica);
        
        carregarDicas();
        mostrarDicaAleatoria();
        
        return view;
    }
    
    private void carregarDicas() {
        dicas = new ArrayList<>();
        try {
            InputStream inputStream = getActivity().getAssets().open("dicas.txt");
            String content = FileUtils.readTextFromAssets(inputStream);
            String[] lines = content.split("\n");
            
            for (String line : lines) {
                if (line.startsWith("dica:")) {
                    String[] parts = line.substring(5).split(",", 2);
                    if (parts.length == 2) {
                        dicas.add(new DicaItem(parts[0].trim(), parts[1].trim()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Adicionar dicas padrão em caso de erro
            adicionarDicasPadrao();
        }
    }
    
    private void adicionarDicasPadrao() {
        dicas.add(new DicaItem("minha_foto1.jpg", "Você é a razão do meu sorriso todos os dias ❤️"));
        dicas.add(new DicaItem("minha_foto2.jpg", "Não existe um dia que eu não pense em você 💕"));
        dicas.add(new DicaItem("minha_foto3.jpg", "Sua felicidade é minha maior conquista 🌟"));
    }
    
    private void mostrarDicaAleatoria() {
        if (!dicas.isEmpty()) {
            Random random = new Random();
            DicaItem dicaAleatoria = dicas.get(random.nextInt(dicas.size()));
            
            try {
                InputStream inputStream = getActivity().getAssets().open(dicaAleatoria.imagePath);
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                imageView.setImageDrawable(drawable);
            } catch (IOException e) {
                // Usar imagem padrão se não encontrar
                imageView.setImageResource(R.drawable.default_heart);
            }
            
            textDica.setText(dicaAleatoria.texto);
        }
    }
    
    private static class DicaItem {
        String imagePath;
        String texto;
        
        DicaItem(String imagePath, String texto) {
            this.imagePath = imagePath;
            this.texto = texto;
        }
    }
}