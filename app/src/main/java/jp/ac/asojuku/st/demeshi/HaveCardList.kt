package jp.ac.asojuku.st.demeshi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_have_card_list.*
import android.R.drawable.*
import android.widget.TextView
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_have_card_list.*
import org.json.JSONObject

class HaveCardList : AppCompatActivity() {

    var user_id = 0
    var CardId = arrayOf(0,0,0,0)

    override fun onCreate(savedInstanceState: Bundle?) {

        user_id = intent.getIntExtra("UserId",0)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_have_card_list)
        MyCardBtn.setOnClickListener{
            val intent = Intent(this,MyCardList::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        HaveCardBtn.setOnClickListener{
            startActivity(Intent(it.context,HaveCardList::class.java))
        }
        PhotoCardBtn.setOnClickListener{
            val intent = Intent(this,PhotoCard::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        HaveCard1.setOnClickListener{
            val intent = Intent(this,HaveCardDetail::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",CardId[1])
            startActivity(intent)
        }
        Name1.setOnClickListener{
            val intent = Intent(this,HaveCardDetail::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",CardId[1])
            startActivity(intent)
        }
        HaveCard2.setOnClickListener{
            val intent = Intent(this,HaveCardDetail::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",CardId[2])
            startActivity(intent)
        }
        Name2.setOnClickListener{
            val intent = Intent(this,HaveCardDetail::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",CardId[2])
            startActivity(intent)
        }
        HaveCard3.setOnClickListener{
            val intent = Intent(this,HaveCardDetail::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",CardId[3])
            startActivity(intent)
        }
        Name3.setOnClickListener{
            val intent = Intent(this,HaveCardDetail::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",CardId[3])
            startActivity(intent)
        }
        HaveCard4.setOnClickListener{
            val intent = Intent(this,HaveCardDetail::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",CardId[4])
            startActivity(intent)
        }
        Name4.setOnClickListener{
            val intent = Intent(this,HaveCardDetail::class.java)
            intent.putExtra("UserId",user_id)
            intent.putExtra("CardId",CardId[4])
            startActivity(intent)
        }

        //力技（実質）動的リスト化
//        MyCard2.setImageResource(screen_background_light)
//        Name2.text = ""
//        CallBtn2.text = ""
//        CallBtn2.background = null
//        MailBtn2.text = ""
//        MailBtn2.background = null
        GetMyCard()
    }

    //所持している名刺一覧
    fun GetMyCard(){
        val NameArray = arrayOf(R.id.Name1, R.id.Name2,R.id.Name3, R.id.Name4)
        val HaveCardArray = arrayOf(HaveCard1,HaveCard2,HaveCard3,HaveCard4)
        val ImgArray = arrayOf(R.drawable.green, R.drawable.f4796, R.drawable.f4788, R.drawable.f4786, R.drawable.f4790, R.drawable.f4791, R.drawable.space, R.drawable.f4782, R.drawable.f4792)
        val CallArray = arrayOf(CallBtn1,CallBtn2,CallBtn3,CallBtn4)
        val MailArray = arrayOf(MailBtn1,MailBtn2,MailBtn3,MailBtn4)

        val URL:String = "http://18001187.pupu.jp/untitled/public/card/collection_return/" + user_id.toString()
        URL.httpGet().responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.array()
                    var cnt = 0
                    var name = ""
                    var NameText: TextView
                    var TemplateId = 0
                    for(i in 0..(json.length())){
                        cnt++
                        name = (json[i] as JSONObject).get("value").toString()
                        NameText = findViewById(NameArray[i]) as TextView
                        NameText.setText(name)
                        TemplateId = (json[i] as JSONObject).get("template_id").toString().toInt()
                        HaveCardArray[i].setImageResource(ImgArray[TemplateId])
                        CardId[i] = (json[i] as JSONObject).get("meisi_id").toString().toInt()
                        if(i==3){
                            break
                        }
                    }
                    for(i in 3..cnt -1){
                        //力技（実質）動的リスト化
                        HaveCardArray[i].setImageResource(screen_background_light)
                        NameText = findViewById(NameArray[i]) as TextView
                        NameText.text = ""
                        CallArray[i].text = ""
                        CallArray[i].background = null
                        MailArray[i].text = ""
                        MailArray[i].background = null
//                        MyCard2.setImageResource(screen_background_light)
//                        Name2.text = ""
//                        CallBtn2.text = ""
//                        CallBtn2.background = null
//                        MailBtn2.text = ""
//                        MailBtn2.background = null
                    }
//                    val json = result.value.obj()
//                    val results = json.get("result")// as JSONArray
//                    if(results == 1){
//                        //取得したもので処理
//                        //名刺ごとの名刺IDをintentでShowMyCardに渡す
//                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
