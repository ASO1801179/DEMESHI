package jp.ac.asojuku.st.demeshi

import android.content.Intent
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
        CompanyName.text="会社名："+companyname
        val jusyo ="世界で一番の最高のエデンNAGASAKI"
        Address.text="住所："+jusyo
        val phoneban= "08064633727"
        PhoneNumber.text="電話番号"+phoneban
        BackBtn.setOnClickListener{startActivity(Intent(it.context,HaveCardDetail::class.java))}
        val mailad = "kinoshtianoyasuragi@gmail.com"
        MaiAddress.text="メアド："+mailad
    }
}
