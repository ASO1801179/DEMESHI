package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show_company.*

class ShowCompany : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_company)
    }
    override fun onResume() {
        super.onResume()
        val companyname ="木下カンパニー"
        company.text="会社名："+companyname
        val jusyo ="世界で一番の最高のエデンNAGASAKI"
        place.text="住所："+jusyo
        val phoneban= "08064633727"
        phone.text="電話番号"+phoneban
        button.setOnClickListener{
            val uri = Uri.parse("$phoneban")
            var intent = Intent(Intent.ACTION_DIAL,uri);
            startActivity(intent);
        }
        val mailad = "kinoshtianoyasuragi@gmail.com"
        mail.text="メアド："+mailad
    }
}
