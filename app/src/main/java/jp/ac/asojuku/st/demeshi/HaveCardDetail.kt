package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_have_card_detail.*

class HaveCardDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_have_card_detail)
        Back.setOnClickListener{startActivity(Intent(it.context,HaveCardList::class.java))}
    }
}
