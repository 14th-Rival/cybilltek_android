package com.dev_enzo.cybilltekapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev_enzo.cybilltekapplication.R
import com.dev_enzo.cybilltekapplication.UserProfileActivity
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
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserInfoAdapter :
    PagingDataAdapter<UserInfo, UserInfoAdapter.UserInfoViewHolder>(object : DiffUtil.ItemCallback<UserInfo>(){
        override fun areItemsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: UserInfo, newItem: UserInfo): Boolean {
            return oldItem == newItem
        }
    }){
    private var mCurUserInfoList: ArrayList<UserInfo> = ArrayList()

    class UserInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivUserInfo: ImageView
        val tvUserInfoFname: TextView
        val tvUserInfoLname: TextView
        val tvUserInfoBday: TextView
        val tvUserInfoAge: TextView
        val tvUserInfoEmail: TextView
        val tvUserInfoContact: TextView
        val fabUserInfoSeeMore: FloatingActionButton

        init {
            ivUserInfo = itemView.findViewById(R.id.iv_user_info)
            tvUserInfoFname = itemView.findViewById(R.id.tv_user_info_fname)
            tvUserInfoLname = itemView.findViewById(R.id.tv_user_info_lname)
            tvUserInfoBday = itemView.findViewById(R.id.tv_user_info_bday)
            tvUserInfoAge = itemView.findViewById(R.id.tv_user_info_age)
            tvUserInfoEmail = itemView.findViewById(R.id.tv_user_info_email)
            tvUserInfoContact = itemView.findViewById(R.id.tv_user_info_contact)
            fabUserInfoSeeMore = itemView.findViewById(R.id.fab_user_info_see_more)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user_info_view, parent, false)
        return UserInfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mCurUserInfoList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserInfoViewHolder, position: Int) {
        val curInfo = mCurUserInfoList[position]
        Glide.with(holder.itemView.context).load(curInfo.getUserMediumImg).circleCrop().into(holder.ivUserInfo)
        holder.tvUserInfoFname.text = "First Name: ${curInfo.getFirstName}"
        holder.tvUserInfoLname.text = "Last Name: ${curInfo.getLastName}"
        holder.tvUserInfoBday.text = "Birthday: ${DateUtil.formatDate(curInfo.getUserBirth)}"
        holder.tvUserInfoAge.text = "Age: ${curInfo.getUserAge}"
        holder.tvUserInfoEmail.text = "Email: ${curInfo.email}"
        holder.tvUserInfoContact.text = "Contact No: ${curInfo.phone}"
        holder.fabUserInfoSeeMore.setOnClickListener{
            Toast.makeText(
                holder.itemView.context,
                "See more details about ${curInfo.getFirstName} ${curInfo.getLastName}",
                Toast.LENGTH_SHORT)
                .show()
            startUserProfileActivity(holder.itemView.context, curInfo)
        }
    }

    /**
     * This will start a new activity to show the full details of the User
     * @param context store the context from the viewholder
     * @param userInfo store the userInfo from mCurUserInfoList
     */
    private fun startUserProfileActivity(context: Context, userInfo: UserInfo) {
        val i = Intent(context, UserProfileActivity::class.java)

        val data = Bundle()
        data.putString(USER_INFO_GENDER, userInfo.gender)
        data.putString(USER_INFO_NAME, userInfo.name)
        data.putString(USER_INFO_LOC, userInfo.location)
        data.putString(USER_INFO_EMAIL, userInfo.email)
        data.putString(USER_INFO_LOGIN, userInfo.login)
        data.putString(USER_INFO_DOB, userInfo.dob)
        data.putString(USER_INFO_REG, userInfo.registered)
        data.putString(USER_INFO_PHONE, userInfo.phone)
        data.putString(USER_INFO_CELL, userInfo.cell)
        data.putString(USER_INFO_ID, userInfo.id)
        data.putString(USER_INFO_PIC, userInfo.picture)
        data.putString(USER_INFO_NAT, userInfo.name)

        i.putExtra("databundle", data)
        context.startActivity(i)
    }

    /**
     * This function works as the basis for storing the data for the
     * Recyclerview Adapter (this)
     * @param newItems store the data (List<UserInfo>) from
     * the observers in the MainActivity
     */
    fun addItems(newItems: List<UserInfo>) {
        val startPosition = mCurUserInfoList.size
        mCurUserInfoList.addAll(newItems)
        notifyItemRangeInserted(startPosition, newItems.size)
    }
}