package com.valentinesday.kamilly.ui.fragments;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PensamentoFragment extends Fragment {
    
    private ImageView imageView;
    private TextView textPensamento;
    private Map<String, String> pensamentos;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pensamento, container, false);
        
        imageView = view.findViewById(R.id.image_pensamento);
        textPensamento = view.findViewById(R.id.text_pensamento);
        
        // Imagem fixa
        imageView.setImageResource(R.drawable.pensamento_fixo);
        
        carregarPensamentos();
        mostrarPensamentoAtual();
        
        return view;
    }
    
    private void carregarPensamentos() {
        pensamentos = new HashMap<>();
        try {
            InputStream inputStream = getActivity().getAssets().open("pensamentos.txt");
            String content = FileUtils.readTextFromAssets(inputStream);
            String[] lines = content.split("\n");
            
            for (String line : lines) {
                String[] parts = line.split(":", 3);
                if (parts.length == 3) {
                    String key = parts[0] + ":" + parts[1];
                    pensamentos.put(key, parts[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            adicionarPensamentosPadrao();
        }
    }
    
    private void adicionarPensamentosPadrao() {
        // Segunda-feira
        pensamentos.put("Monday:8", "Começando a semana pensando em você ❤️");
        pensamentos.put("Monday:12", "Hora do almoço e você na minha mente 💕");
        pensamentos.put("Monday:18", "Fim do dia, só quero te ver 🥰");
        pensamentos.put("Monday:22", "Boa noite, meu amor. Sonharei com você 😘");
        
        // Terça-feira
        pensamentos.put("Tuesday:8", "Terça-feira fica melhor pensando em nós 💖");
        pensamentos.put("Tuesday:12", "Meio-dia e meu coração bate por você 💓");
        pensamentos.put("Tuesday:18", "Cada hora que passa, te amo mais 🌟");
        pensamentos.put("Tuesday:22", "Noite estrelada como seus olhos ✨");
        
        // Quarta-feira
        pensamentos.put("Wednesday:8", "Meio da semana, mas você é sempre meu foco 💘");
        pensamentos.put("Wednesday:12", "Almoçando e imaginando nosso próximo encontro 😍");
        pensamentos.put("Wednesday:18", "Quarta à noite pensando no seu sorriso 😊");
        pensamentos.put("Wednesday:22", "Durma bem, princesa dos meus sonhos 👑");
        
        // Continue para outros dias...
        // Adicionar mais horários e dias conforme necessário
    }
    
    private void mostrarPensamentoAtual() {
        Calendar calendar = Calendar.getInstance();
        String dayOfWeek = getDayOfWeekString(calendar.get(Calendar.DAY_OF_WEEK));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        
        // Buscar pensamento mais próximo
        String pensamento = buscarPensamento(dayOfWeek, hour);
        textPensamento.setText(pensamento);
    }
    
    private String getDayOfWeekString(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.MONDAY: return "Monday";
            case Calendar.TUESDAY: return "Tuesday";
            case Calendar.WEDNESDAY: return "Wednesday";
            case Calendar.THURSDAY: return "Thursday";
            case Calendar.FRIDAY: return "Friday";
            case Calendar.SATURDAY: return "Saturday";
            case Calendar.SUNDAY: return "Sunday";
            default: return "Monday";
        }
    }
    
    private String buscarPensamento(String day, int hour) {
        // Buscar pensamento exato
        String key = day + ":" + hour;
        if (pensamentos.containsKey(key)) {
            return pensamentos.get(key);
        }
        
        // Buscar o mais próximo
        int[] horariosComuns = {8, 12, 18, 22};
        int horaProxima = horariosComuns[0];
        int menorDiferenca = Math.abs(hour - horariosComuns[0]);
        
        for (int h : horariosComuns) {
            int diferenca = Math.abs(hour - h);
            if (diferenca < menorDiferenca) {
                menorDiferenca = diferenca;
                horaProxima = h;
            }
        }
        
        String keyProxima = day + ":" + horaProxima;
        return pensamentos.getOrDefault(keyProxima, "Pensando em você sempre, meu amor! 💕");
    }
}