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
import kotlinx.android.synthetic.main.activity_create_company.*
import kotlinx.android.synthetic.main.activity_create_individual.*
import kotlinx.android.synthetic.main.activity_create_individual.Address
import kotlinx.android.synthetic.main.activity_create_individual.Back
import kotlinx.android.synthetic.main.activity_create_individual.BackDesign
import kotlinx.android.synthetic.main.activity_create_individual.CreateBtn
import kotlinx.android.synthetic.main.activity_create_individual.EditAddress
import kotlinx.android.synthetic.main.activity_create_individual.EditMail
import kotlinx.android.synthetic.main.activity_create_individual.EditName
import kotlinx.android.synthetic.main.activity_create_individual.EditPhone
import kotlinx.android.synthetic.main.activity_create_individual.Mail
import kotlinx.android.synthetic.main.activity_create_individual.Name1
import kotlinx.android.synthetic.main.activity_create_individual.Phone

class CreateIndividual : AppCompatActivity() {
    var user_id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)
        Back.setOnClickListener{startActivity(Intent(it.context,Template::class.java))}
        user_id = intent.getIntExtra("UserId",0)

        when(intent.getIntExtra("Image",0)){
            4->{
                BackDesign.setImageResource(f4786)
            }
            5->{
                BackDesign.setImageResource(f4790)
            }
            6->{
                BackDesign.setImageResource(f4791)
            }
        }
        Name1.bringToFront()
        Phone.bringToFront()
        Mail.bringToFront()
        Address.bringToFront()
        CreateBtn.setOnClickListener{CardCreate()}
    }

    override fun onResume() {
        super.onResume()
        Name1.text = "情報太郎"
        Phone.text = "0120-00-2229"
        Mail.text = "zyouhou@gmial.com"
        Address.text = "@zyouhoutarou"
    }

    fun CardCreate(){
        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/card/normal_add"
        //val URL:String = "http://18001187.pupu.jp/untitled/public/card/insert/" + user_id
        val user_id = Pair("user_id",Integer.toString(user_id))
        val name = Pair("name",Name1.text.toString())
        val phone = Pair("number",Phone.text.toString())
        val mailaddress = Pair("address",Mail.text.toString())
        val sns = Pair("sns",Address.text.toString())
        val img =Pair("img",Integer.toString(intent.getIntExtra("Image",0)))
        val pair = listOf<Pair<String,String>>(user_id,name,phone,mailaddress,sns,img)
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
                    println(request)
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
