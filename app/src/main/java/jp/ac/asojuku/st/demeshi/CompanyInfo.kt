package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_company_info.*

class CompanyInfo : AppCompatActivity() {

    val user_id = intent.getIntExtra("UserId",0)
    val img = intent.getIntExtra("Image",0)
    val confirm = intent.getStringExtra("Confirm")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_info)
        if(confirm == "Company"){
            CompanyText.text = "企業IDと企業パスワードを設定してください"
            CompanyBtn.text = "登録"
        }else{
            CompanyText.text = "企業IDと企業パスワードでログインしてください"
            CompanyBtn.text = "紐づける"
        }
        CompanyBtn.setOnClickListener{
            if(Signal()){
                if(img <= 6){
                    val intent = Intent(this,CreateIndividual::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("Image",img)
                    startActivity(intent)
                }else{
                    val intent = Intent(this,CreateCompany::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("Image",img)
                    startActivity(intent)
                }
            }

        }
    }
    fun Signal():Boolean{
        var bool = true
        val company_id = Pair("company_id", CompanyID.toString())
        val company_password = Pair("company_password", CompanyPass.toString())
        val URL:String = "http://18001187.pupu.jp/untitled/public/user/login"
        val pair = listOf<Pair<String,String>>(company_id,company_password)
        URL.httpGet(pair).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")// as JSONArray
                    if(results == 0){
                        bool = false
                        //Toast.makeText(this, "ログインしました", Toast.LENGTH_LONG).show()
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
        return bool
    }
}
