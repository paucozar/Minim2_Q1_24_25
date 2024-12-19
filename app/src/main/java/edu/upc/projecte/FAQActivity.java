package edu.upc.projecte;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FAQActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFaq;
    private FAQAdapter faqAdapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        recyclerViewFaq = findViewById(R.id.recyclerViewFaq);
        recyclerViewFaq.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        loadFaqs();
    }

    private void loadFaqs() {
        Call<List<FAQ>> call = apiService.getAllFaqs();
        call.enqueue(new Callback<List<FAQ>>() {
            @Override
            public void onResponse(Call<List<FAQ>> call, Response<List<FAQ>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FAQ> faqList = response.body();
                    faqAdapter = new FAQAdapter(faqList);
                    recyclerViewFaq.setAdapter(faqAdapter);
                } else {
                    Toast.makeText(FAQActivity.this, "Error, no se han podido cargar las preguntas frecuentes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FAQ>> call, Throwable t) {
                Toast.makeText(FAQActivity.this, "Error, no se han podido cargar las preguntas frecuentes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
