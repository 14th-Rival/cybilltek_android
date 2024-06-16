package com.dev_enzo.cybilltekapplication

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dev_enzo.cybilltekapplication.base.BaseActivity
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_CELL
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_DOB
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_EMAIL
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_GENDER
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_ID
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_LOC
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_LOGIN
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_NAME
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_NAT
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_PHONE
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_PIC
import com.dev_enzo.domain.GlobalConstants.Companion.USER_INFO_REG
import com.dev_enzo.data.bean.UserInfo
import com.dev_enzo.domain.utils.DateUtil
import jp.wasabeef.glide.transformations.BlurTransformation

class UserProfileActivity : BaseActivity() {

    private var mUserInfo: UserInfo? = null
    private lateinit var ivUserInfoProfileBg: ImageView
    private lateinit var ivUserInfoProfile: ImageView
    private lateinit var ibtnUserInfoProfile: ImageButton

    private lateinit var tvUserInfoProfileFname: TextView
    private lateinit var tvUserInfoProfileLname: TextView
    private lateinit var tvUserInfoProfileDob: TextView
    private lateinit var tvUserInfoProfileAge: TextView
    private lateinit var tvUserInfoProfileEmail: TextView
    private lateinit var tvUserInfoProfileMobile: TextView
    private lateinit var tvUserInfoProfileAddress: TextView
    private lateinit var tvUserInfoProfileMember: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUserProfileData()
        initUserElements()
    }

    /**
     * This function serves as the endpoint for receiving
     * the data from UserInfoAdapter, it is synchronized
     * to prevent the code from executing concurrently
     */
    @Synchronized
    protected fun initUserProfileData() {
        val data = intent.extras!!.getBundle("databundle")
        if (data != null) {
            if (mUserInfo == null) {
                mUserInfo = UserInfo(
                    data.getString(USER_INFO_GENDER)!!,
                    data.getString(USER_INFO_NAME)!!,
                    data.getString(USER_INFO_LOC)!!,
                    data.getString(USER_INFO_EMAIL)!!,
                    data.getString(USER_INFO_LOGIN)!!,
                    data.getString(USER_INFO_DOB)!!,
                    data.getString(USER_INFO_REG)!!,
                    data.getString(USER_INFO_PHONE)!!,
                    data.getString(USER_INFO_CELL)!!,
                    data.getString(USER_INFO_ID)!!,
                    data.getString(USER_INFO_PIC)!!,
                    data.getString(USER_INFO_NAT)!!,
                )
            }
        }
    }

    /**
     * This function initializes the view elements from view
     * once done it will the load the data from mUserInfo variable.
     * Annotated as sync to prevent multiple concurrent execution
     */
    @Synchronized
    protected fun initUserElements() {
        ivUserInfoProfileBg = findViewById(R.id.iv_user_info_profile_bg)
        ivUserInfoProfile = findViewById(R.id.iv_user_info_profile)
        ibtnUserInfoProfile = findViewById(R.id.ibtn_back)
        tvUserInfoProfileFname = findViewById(R.id.tv_user_info_profile_fname)
        tvUserInfoProfileLname = findViewById(R.id.tv_user_info_profile_lname)
        tvUserInfoProfileDob = findViewById(R.id.tv_user_info_profile_dob)
        tvUserInfoProfileAge = findViewById(R.id.tv_user_info_profile_age)
        tvUserInfoProfileEmail = findViewById(R.id.tv_user_info_profile_email)
        tvUserInfoProfileMobile = findViewById(R.id.tv_user_info_profile_mobile)
        tvUserInfoProfileAddress = findViewById(R.id.tv_user_info_profile_address)
        tvUserInfoProfileMember = findViewById(R.id.tv_user_info_profile_member)

        loadImageBackground()
        loadImageProfile()
        eventListener()
        loadInfoProfile()
    }

    /**
     * This function will load the background Image
     * for ivUserInfoProfileBg element
     */
    protected fun loadImageBackground() {
        Glide.with(this)
            .load(mUserInfo!!.getUserLargeImg)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(ivUserInfoProfileBg)
    }

    /**
     * This function will load the background Image
     * for ivUserInfoProfile element
     */
    protected fun loadImageProfile() {
        Glide.with(this)
            .load(mUserInfo!!.getUserLargeImg)
            .circleCrop()
            .into(ivUserInfoProfile)
    }

    /**
     * This function will listen to event clicks
     * from the user, once clicked it will go back to
     * the previous activity
     */
    protected fun eventListener() {
        ibtnUserInfoProfile.setOnClickListener {
            onBackPressed()
        }
    }

    /**
     * This function will load the data and will
     * be assigned to their respective UI elements
     */
    protected fun loadInfoProfile() {
        val userInfo = mUserInfo!!
        tvUserInfoProfileFname.text = userInfo.getFirstName
        tvUserInfoProfileLname.text = userInfo.getLastName
        tvUserInfoProfileDob.text = DateUtil.formatDate(userInfo.getUserBirth)
        tvUserInfoProfileAge.text = userInfo.getUserAge.toString()
        tvUserInfoProfileEmail.text = userInfo.email
        tvUserInfoProfileMobile.text = userInfo.phone
        tvUserInfoProfileAddress.text = userInfo.getFullAddress
        tvUserInfoProfileMember.text = "${DateUtil.getYear(userInfo.getUserRegistrationDate)}\nMember"

    }
}