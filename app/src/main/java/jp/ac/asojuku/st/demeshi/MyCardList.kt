package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_my_card_list.*

class MyCardList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_card_list)
        MyCardBtn.setOnClickListener{startActivity(Intent(it.context,MyCardList::class.java))}
        HaveCardBtn.setOnClickListener{startActivity(Intent(it.context,HaveCardList::class.java))}
        PhotoCardBtn.setOnClickListener{startActivity(Intent(it.context,PhotoCard::class.java))}
        AddBtn.setOnClickListener{startActivity(Intent(it.context,Template::class.java))}
        MyCard.setOnClickListener{startActivity(Intent(it.context,ShowMyCard::class.java))}
    }
}
