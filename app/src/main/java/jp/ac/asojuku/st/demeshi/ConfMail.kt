package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_conf_mail.*

class ConfMail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conf_mail)
        BackLogin.setOnClickListener{startActivity(Intent(it.context,Login::class.java))}
        if(intent.getStringExtra("TextFlag") == "Register"){
            ConfText.setText(R.string.ConfRegisterText)
        }else if(intent.getStringExtra("TextFlag") == "Forgot"){
            ConfText.setText(R.string.ConfForgotText)
        }
    }
}
