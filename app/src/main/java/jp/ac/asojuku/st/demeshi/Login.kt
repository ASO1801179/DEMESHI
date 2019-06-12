package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        LoginBtn.setOnClickListener{startActivity(Intent(it.context,MyCardList::class.java))}
        RegisterBtn.setOnClickListener{startActivity(Intent(it.context,Register::class.java))}
        ForgotBtn.setOnClickListener{startActivity(Intent(it.context,ForgotPassword::class.java))}
    }
}
