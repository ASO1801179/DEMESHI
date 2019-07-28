package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import jp.ac.asojuku.st.demeshi.R.drawable.*
import kotlinx.android.synthetic.main.activity_create_card.*

class CreateCard : AppCompatActivity() {

    var user_id = 0
    var company_name = ""
    var company_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        user_id = intent.getIntExtra("UserId",0)
        company_name = intent.getStringExtra("company_name")
        company_id = intent.getIntExtra("company_id",0)

        Back.setOnClickListener{startActivity(Intent(it.context,Template::class.java))}
        CreateBtn.setOnClickListener{Create()}
        when(intent.getIntExtra("Image",0)){
            1->{
                BackDesign.setImageResource(green)
            }
            2->{
                BackDesign.setImageResource(f4796)

            }
            3->{
                BackDesign.setImageResource(f4788)
            }
        }
        Name1.bringToFront()
        Phone.bringToFront()
        Mail.bringToFront()
        Address.bringToFront()



    }

    override fun onResume() {
        super.onResume()
        Name1.text = "情報太郎"
        Phone.text = "xxx-xxx-xxxx"
        Mail.text = "xxxx@gmail.co,"
        Address.text = "xxxxx.co.jp"
    }

    fun Create(){
        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/card/business_add"
        //val URL:String = "http://18001187.pupu.jp/untitled/public/card/insert/" + user_id
        val user_id = Pair("user_id",Integer.toString(user_id))
        val name = Pair("name",Name1.text.toString())
        val phone = Pair("number",Phone.text.toString())
        val mailaddress = Pair("address",Mail.text.toString())
        val img =Pair("img",Integer.toString(intent.getIntExtra("Image",0)))
        val company_id = Pair("company_id",Integer.toString(company_id))
        val pair = listOf<Pair<String,String>>(user_id,name,phone,mailaddress,img,company_id)
        URL.httpGet(pair).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.obj()
                    val results = json.get("result")// as JSONArray

                    //作成成功時それ以外は失敗
                    if(results == 1){
                        val intent = Intent(this,MyCardList::class.java)
                        intent.putExtra("UserId",user_id)
                        Toast.makeText(this, "名刺作成!!", Toast.LENGTH_LONG).show()
                        startActivity(intent)
                    }else{

                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
