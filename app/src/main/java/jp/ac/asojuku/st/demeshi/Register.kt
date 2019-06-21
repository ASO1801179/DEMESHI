package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        RegisterBtn.setOnClickListener{Register()}
    }
    fun Register(){
        val intent = Intent(this,ConfMail::class.java)
        intent.putExtra("TextFlag","Register")
        startActivity(intent)
    }
}
