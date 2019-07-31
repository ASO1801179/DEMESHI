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
import jp.ac.asojuku.st.demeshi.R.drawable.*
import org.json.JSONObject

class HaveCardList : AppCompatActivity() {

    var user_id = 0
    var CardId = arrayOf(0,0,0,0)
    //完全個人0
    var CompanyFlag = arrayOf(0,0,0,0)
    var Flag = arrayOf(false,false,false,false)

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
            val intent = Intent(this,HaveCardList::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        PhotoCardBtn.setOnClickListener{
            val intent = Intent(this,PhotoCard::class.java)
            intent.putExtra("UserId",user_id)
            startActivity(intent)
        }
        HaveCard1.setOnClickListener{
            if(Flag[0]){
                if(CompanyFlag[0] == 0){
                    val intent = Intent(this,HaveCardIndividual::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[0])
                    startActivity(intent)
                }else{
                    val intent = Intent(this,HaveCardDetail::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[0])
                    startActivity(intent)
                }
            }
        }
        Name1.setOnClickListener{
            if(Flag[0]){
                if(CompanyFlag[0] == 0){
                    val intent = Intent(this,HaveCardIndividual::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[0])
                    startActivity(intent)
                }else{
                    val intent = Intent(this,HaveCardDetail::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[0])
                    startActivity(intent)
                }
            }
        }
        HaveCard2.setOnClickListener{
            if(Flag[1]){
                if(CompanyFlag[1] == 0){
                    val intent = Intent(this,HaveCardIndividual::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[1])
                    startActivity(intent)
                }else{
                    val intent = Intent(this,HaveCardDetail::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[1])
                    startActivity(intent)
                }
            }
        }
        Name2.setOnClickListener{
            if(Flag[1]){
                if(CompanyFlag[1] == 0){
                    val intent = Intent(this,HaveCardIndividual::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[1])
                    startActivity(intent)
                }else{
                    val intent = Intent(this,HaveCardDetail::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[1])
                    startActivity(intent)
                }
            }
        }
        HaveCard3.setOnClickListener{
            if(Flag[2]){
                if(CompanyFlag[2] == 0){
                    val intent = Intent(this,HaveCardIndividual::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[2])
                    startActivity(intent)
                }else{
                    val intent = Intent(this,HaveCardDetail::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[2])
                    startActivity(intent)
                }
            }
        }
        Name3.setOnClickListener{
            if(Flag[2]){
                if(CompanyFlag[2] == 0){
                    val intent = Intent(this,HaveCardIndividual::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[2])
                    startActivity(intent)
                }else{
                    val intent = Intent(this,HaveCardDetail::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[2])
                    startActivity(intent)
                }
            }
        }
        HaveCard4.setOnClickListener{
            if(Flag[3]){
                if(CompanyFlag[3] == 0){
                    val intent = Intent(this,HaveCardIndividual::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[3])
                    startActivity(intent)
                }else{
                    val intent = Intent(this,HaveCardDetail::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[3])
                    startActivity(intent)
                }
            }
        }
        Name4.setOnClickListener{
            if(Flag[3]){
                if(CompanyFlag[3] == 0){
                    val intent = Intent(this,HaveCardIndividual::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[3])
                    startActivity(intent)
                }else{
                    val intent = Intent(this,HaveCardDetail::class.java)
                    intent.putExtra("UserId",user_id)
                    intent.putExtra("CardId",CardId[3])
                    startActivity(intent)
                }
            }
        }
        GetMyCard()
    }

    //所持している名刺一覧
    fun GetMyCard(){
        val NameArray = arrayOf(R.id.Name1, R.id.Name2,R.id.Name3, R.id.Name4)
        val HaveCardArray = arrayOf(HaveCard1,HaveCard2,HaveCard3,HaveCard4)
        val ImgArray = arrayOf(new1,new2,new3,new4,new5,new6,new7,new8,new9)
        val CallArray = arrayOf(CallBtn1,CallBtn2,CallBtn3,CallBtn4)
        val MailArray = arrayOf(MailBtn1,MailBtn2,MailBtn3,MailBtn4)

        val URL:String = "http://kinoshitadaiki.bitter.jp/newDEMESI/public/list/others_card"
        URL.httpGet(listOf("user_id" to user_id)).responseJson() { request, response, result ->
            when (result) {
                is Result.Success -> {
                    // レスポンスボディを表示
                    val json = result.value.array()
                    var cnt = 0
                    var name = ""
                    var NameText: TextView
                    var TemplateId = 0
                    for(i in 0..(json.length()-1)){
                        cnt++
                        name = (json[i] as JSONObject).get("name").toString()
                        NameText = findViewById(NameArray[i]) as TextView
                        NameText.setText(name)
                        TemplateId = (json[i] as JSONObject).get("img").toString().toInt()
                        HaveCardArray[i].setImageResource(ImgArray[TemplateId-1])
                        CardId[i] = (json[i] as JSONObject).get("meisi_id").toString().toInt()
                        CompanyFlag[i] = (json[i] as JSONObject).get("flag").toString().toInt()
                        Flag[i] = true
                        if(i==3){
                            break
                        }
                    }
                    if(cnt != 4) {
                        for (i in 0..(3-cnt)) {
                            //力技（実質）動的リスト化
                            HaveCardArray[3-i].setImageResource(screen_background_light)
                            NameText = findViewById(NameArray[3-i]) as TextView
                            NameText.text = ""
                            CallArray[3-i].text = ""
                            CallArray[3-i].background = null
                            MailArray[3-i].text = ""
                            MailArray[3-i].background = null
                        }
                    }
                }
                is Result.Failure -> {
                    println("通信に失敗しました。")
                }
            }
        }
    }
}
