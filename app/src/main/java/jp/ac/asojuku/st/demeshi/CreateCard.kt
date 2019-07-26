package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import jp.ac.asojuku.st.demeshi.R.drawable.*
import kotlinx.android.synthetic.main.activity_create_card.*

class CreateCard : AppCompatActivity() {

    val user_id = intent.getIntExtra("UserId",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
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
        Name1.text = EditName.text
        Phone.text = EditPhone.text
        Mail.text = EditMail.text
        Address.text = EditAddress.text
    }

    fun Create(){
        val URL:String = "http://18001187.pupu.jp/untitled/public/card/insert"
        //val URL:String = "http://18001187.pupu.jp/untitled/public/card/insert/" + user_id
        val userId = Pair("user_id",user_id.toString())
        val name = Pair("name",Name1.text.toString())
        val phone = Pair("phone",Phone.text.toString())
        val mailaddress = Pair("mailaddress",Mail.text.toString())
        val number = Pair("number",Address.text.toString())
        val pair = listOf<Pair<String,String>>(userId,name,phone,mailaddress,number)
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
