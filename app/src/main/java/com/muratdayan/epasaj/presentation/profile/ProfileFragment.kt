package com.muratdayan.epasaj.presentation.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.muratdayan.epasaj.presentation.base.SharedPrefManager
import com.muratdayan.epasaj.databinding.FragmentProfileBinding
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoModel
import com.muratdayan.epasaj.presentation.base.BaseFragment
import com.muratdayan.epasaj.presentation.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var userInfo: UserInfoModel
    private val args: ProfileFragmentArgs by navArgs()

    // base fragmenttan binding alınımı
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        // sharedden token alınımı
        val sharedPrefManager = SharedPrefManager(requireContext(), "user_data")
        //val userId = sharedPrefManager.getValue("user_id", 0)
        val token = sharedPrefManager.getValue("user_token", "")

        if (token.isNotEmpty()) {
            sharedViewModel.getUserDetailsByToken(token)
        }

        val userInfoModel = args.userInfo

        // args olarak detailden userınfo gelmiyorsa servisteki userinfo dinlenir
        if (userInfoModel != null) {
            userInfo = userInfoModel
            printUserInfos(userInfo)
        } else {
            lifecycleScope.launch {
                sharedViewModel.userInfoState.collect { userInfoState ->
                    when {
                        userInfoState.userInfo != null -> {
                            userInfo = userInfoState.userInfo
                            printUserInfos(userInfo)
                        }
                    }
                }
            }
        }
        // Tıklanılan cardviewlara göre hangi cardview tıklanmışsa onun section bilgileri gönderilir

        binding.imgViewBioEdit.setOnClickListener {
            val action = ProfileFragmentDirections.navigateProfileFragmentToProfileEditFragment(userInfo = userInfo, section = "user")
            Navigation.findNavController(it).navigate(action)
        }

        binding.imgViewCompanyEdit.setOnClickListener {
            val action = ProfileFragmentDirections.navigateProfileFragmentToProfileEditFragment(userInfo = userInfo, section = "company")
            Navigation.findNavController(it).navigate(action)
        }

        binding.imgViewBankEdit.setOnClickListener {
            val action = ProfileFragmentDirections.navigateProfileFragmentToProfileEditFragment(userInfo = userInfo, section = "bank")
            Navigation.findNavController(it).navigate(action)
        }

        binding.imgViewAddressEdit.setOnClickListener {
            val action = ProfileFragmentDirections.navigateProfileFragmentToProfileEditFragment(userInfo = userInfo, section = "address")
            Navigation.findNavController(it).navigate(action)
        }

        binding.imgViewBackIcon.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        return view
    }

    // bileşenlere verilerin yazılması
    @SuppressLint("SetTextI18n")
    private fun printUserInfos(userInfo: UserInfoModel) {
        binding.txtViewUsername.text = userInfo.username
        binding.txtViewAge.text = "Age : ${userInfo.age}"
        binding.txtViewGender.text = "Gender : ${userInfo.gender}"
        binding.txtViewEmail.text = "Email : ${userInfo.email}"
        binding.txtViewPhone.text = "Phone : ${userInfo.phone}"
        binding.txtViewBirthdate.text = "Birthdate : ${userInfo.birthDate}"
        binding.txtViewBloodgroup.text = "Bloodgroup : ${userInfo.bloodGroup}"
        binding.txtViewHeight.text = "Height : ${userInfo.height}"
        binding.txtViewWeight.text = "Weight : ${userInfo.weight}"
        binding.txtViewEyecolor.text = "Eyecolor : ${userInfo.eyeColor}"
        Glide.with(requireContext())
            .load(userInfo.image)
            .into(binding.imgViewUser)
        binding.txtViewAddress.text = "Address : ${userInfo.address?.address}"
        binding.txtViewCity.text = "City : ${userInfo.address?.city}"
        binding.txtViewState.text = "State : ${userInfo.address?.state}"
        binding.txtViewUniversity.text = "University : ${userInfo.university}"
        binding.txtViewDepartment.text = "Department : ${userInfo.company?.department}"
        binding.txtViewCompanyName.text = "Company : ${userInfo.company?.name}"
        binding.txtViewCompanyTitle.text = "Title : ${userInfo.company?.title}"
        binding.txtViewCompanyAddress.text = "Company Address : ${userInfo.company?.address?.address}"
        binding.txtViewCompanyCity.text = "Company City : ${userInfo.company?.address?.city}"
        binding.txtViewCompanyState.text = "Company State : ${userInfo.company?.address?.state}"
        binding.txtViewCompanyCountry.text = "Company Country : ${userInfo.company?.address?.country}"
        binding.txtViewBankCardNumber.text = "Bank Card Number : ${userInfo.bank?.cardNumber}"
        binding.txtViewBankCardExpire.text = "Bank Card Expire : ${userInfo.bank?.cardExpire}"
        binding.txtViewBankCardType.text = "Bank Card Type : ${userInfo.bank?.cardType}"
        binding.txtViewBankCurrency.text = "Bank Currency : ${userInfo.bank?.currency}"
        binding.txtViewBankIban.text = "Bank Iban : ${userInfo.bank?.iban}"
    }
}
