package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show_company.*

class ShowCompany : AppCompatActivity() {

    val user_id = intent.getIntExtra("UserId",0)
    var card_id = intent.getIntExtra("CardId",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_company)
    }
    override fun onResume() {
        super.onResume()
        BackBtn.setOnClickListener{startActivity(Intent(it.context,HaveCardDetail::class.java))}
        //名刺IDをとって企業名刺の情報表示
    }
}
