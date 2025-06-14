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

public class InsegurancaFragment extends Fragment {
    
    private ImageView imageView;
    private TextView textInseguranca;
    private List<FraseItem> frases;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inseguranca, container, false);
        
        imageView = view.findViewById(R.id.image_inseguranca);
        textInseguranca = view.findViewById(R.id.text_inseguranca);
        
        carregarFrases();
        mostrarFraseAleatoria();
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // Mudar frase toda vez que entrar na tela
        mostrarFraseAleatoria();
    }
    
    private void carregarFrases() {
        frases = new ArrayList<>();
        try {
            InputStream inputStream = getActivity().getAssets().open("insegurancas.txt");
            String content = FileUtils.readTextFromAssets(inputStream);
            String[] lines = content.split("\n");
            
            for (String line : lines) {
                if (line.startsWith("frase:")) {
                    String[] parts = line.substring(6).split(",", 2);
                    if (parts.length == 2) {
                        frases.add(new FraseItem(parts[0].trim(), parts[1].trim()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            adicionarFrasesPadrao();
        }
    }
    
    private void adicionarFrasesPadrao() {
        frases.add(new FraseItem("minha_foto_amor1.jpg", "É óbvio que eu te amo! 💖"));
        frases.add(new FraseItem("minha_foto_amor2.jpg", "Não sei por que pergunta, você é tudo pra mim! 😘"));
        frases.add(new FraseItem("minha_foto_amor3.jpg", "Claro que te amo, bobinha! Mais que tudo! 💕"));
        frases.add(new FraseItem("minha_foto_amor4.jpg", "Óbvio demais! Você é minha vida! ❤️"));
        frases.add(new FraseItem("minha_foto_amor5.jpg", "Sempre te amarei, não duvide disso! 🥰"));
    }
    
    private void mostrarFraseAleatoria() {
        if (!frases.isEmpty()) {
            Random random = new Random();
            FraseItem fraseAleatoria = frases.get(random.nextInt(frases.size()));
            
            try {
                InputStream inputStream = getActivity().getAssets().open(fraseAleatoria.imagePath);
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                imageView.setImageDrawable(drawable);
            } catch (IOException e) {
                imageView.setImageResource(R.drawable.default_heart);
            }
            
            textInseguranca.setText(fraseAleatoria.texto);
        }
    }
    
    private static class FraseItem {
        String imagePath;
        String texto;
        
        FraseItem(String imagePath, String texto) {
            this.imagePath = imagePath;
            this.texto = texto;
        }
    }
}