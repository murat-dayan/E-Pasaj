package com.muratdayan.epasaj.presentation.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.muratdayan.epasaj.R
import com.muratdayan.epasaj.databinding.FragmentProfileEditBinding
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoModel
import com.muratdayan.epasaj.presentation.base.BaseFragment
import com.muratdayan.epasaj.presentation.base.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileEditFragment : BaseFragment<FragmentProfileEditBinding>() {

    private val args: ProfileEditFragmentArgs by navArgs()
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var user: UserInfoModel

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileEditBinding {
        return FragmentProfileEditBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val sharedPrefManager = SharedPrefManager(requireContext(), "user_data")
        val userId = sharedPrefManager.getValue("user_id", 0)

        binding.imgViewBackIcon.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        user = args.userInfo
        val section = args.section

        // Tüm EditText'leri başlangıçta görünmez yap
        binding.editTxt1.visibility = View.GONE
        binding.editTxt2.visibility = View.GONE
        binding.editTxt3.visibility = View.GONE
        binding.editTxt4.visibility = View.GONE
        binding.editTxt5.visibility = View.GONE
        binding.editTxt6.visibility = View.GONE

        // Gerekli EditText'leri görünür yap ve içeriğini set et
        when (section) {
            "address" -> {
                setEditTextVisible(binding.editTxt1, user.address?.address)
                setHint(binding.textInputLayout1, "Address")
                setEditTextVisible(binding.editTxt2, user.address?.city)
                setHint(binding.textInputLayout2, "City")
                setEditTextVisible(binding.editTxt3, user.address?.state)
                setHint(binding.textInputLayout3, "State")
                setEditTextVisible(binding.editTxt4, user.address?.country)
                setHint(binding.textInputLayout4, "Country")
                setupDoneButton {
                    val address = binding.editTxt1.text.toString()
                    val city = binding.editTxt2.text.toString()
                    val state = binding.editTxt3.text.toString()
                    val country = binding.editTxt4.text.toString()

                    if (address.isNotEmpty() && city.isNotEmpty() && state.isNotEmpty() && country.isNotEmpty()) {
                        val addressModel = user.address?.copy(
                            address = address,
                            city = city,
                            state = state,
                            country = country
                        )
                        user = user.copy(address = addressModel)
                        updateUser(userId, user)
                    }
                }
            }

            "company" -> {
                setEditTextVisible(binding.editTxt1, user.company?.department)
                setHint(binding.textInputLayout1, "Department")
                setEditTextVisible(binding.editTxt2, user.company?.name)
                setHint(binding.textInputLayout2, "Name")
                setEditTextVisible(binding.editTxt3, user.company?.title)
                setHint(binding.textInputLayout3, "Title")
                setEditTextVisible(binding.editTxt4, user.company?.address?.address)
                setHint(binding.textInputLayout4, "Address")
                setEditTextVisible(binding.editTxt5, user.company?.address?.city)
                setHint(binding.textInputLayout5, "City")
                setEditTextVisible(binding.editTxt6, user.company?.address?.state)
                setHint(binding.textInputLayout6, "State")
                setupDoneButton {
                    val companyDepartment = binding.editTxt1.text.toString()
                    val companyName = binding.editTxt2.text.toString()
                    val companyTitle = binding.editTxt3.text.toString()
                    val companyAddress = binding.editTxt4.text.toString()
                    val companyCity = binding.editTxt5.text.toString()
                    val companyState = binding.editTxt6.text.toString()

                    if (
                        companyDepartment.isNotEmpty() &&
                        companyName.isNotEmpty() &&
                        companyTitle.isNotEmpty() &&
                        companyAddress.isNotEmpty() &&
                        companyCity.isNotEmpty() &&
                        companyState.isNotEmpty()
                    ) {
                        val addressModel = user.company?.address?.copy(address = companyAddress, city = companyCity, state = companyState)
                        val companyModel = user.company?.copy(address = addressModel, department = companyDepartment, name = companyName, title = companyTitle)
                        user = user.copy(company = companyModel)
                        updateUser(userId, user)
                    }
                }
            }

            "bank" -> {
                setEditTextVisible(binding.editTxt1, user.bank?.cardExpire)
                setHint(binding.textInputLayout1, "Card Expire")
                setEditTextVisible(binding.editTxt2, user.bank?.cardNumber,)
                setHint(binding.textInputLayout2, "Card Number")
                setEditTextVisible(binding.editTxt3, user.bank?.cardType, )
                setHint(binding.textInputLayout3, "Card Type")
                setEditTextVisible(binding.editTxt4, user.bank?.iban, )
                setHint(binding.textInputLayout4, "IBAN")
                setupDoneButton {
                    val cardExpire = binding.editTxt1.text.toString()
                    val cardNumber = binding.editTxt2.text.toString()
                    val cardType = binding.editTxt3.text.toString()
                    val iban = binding.editTxt4.text.toString()

                    if (cardExpire.isNotEmpty() && cardNumber.isNotEmpty() && cardType.isNotEmpty() && iban.isNotEmpty()) {
                        val userInfoBankModel = user.bank?.copy(
                            cardExpire = cardExpire,
                            cardNumber = cardNumber,
                            cardType = cardType,
                            iban = iban
                        )
                        user = user.copy(bank = userInfoBankModel)
                        updateUser(userId, user)
                    }
                }
            }

            "user" -> {
                setEditTextVisible(binding.editTxt1, user.username,)
                setHint(binding.textInputLayout1, "Username")
                setEditTextVisible(binding.editTxt2, user.phone, )
                setHint(binding.textInputLayout2, "Phone")
                setEditTextVisible(binding.editTxt3, user.email)
                setHint(binding.textInputLayout3, "Email")
                setupDoneButton {
                    val username = binding.editTxt1.text.toString()
                    val phone = binding.editTxt2.text.toString()
                    val email = binding.editTxt3.text.toString()

                    if (username.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
                        user = user.copy(username = username, phone = phone, email = email)
                        updateUser(userId, user)
                    }
                }
            }
        }
        return view
    }

    private fun setEditTextVisible(editText: EditText, text: String?) {
        if (text != null) {
            editText.visibility = View.VISIBLE
            editText.isFocusable = true
            editText.isFocusableInTouchMode = true
            editText.setText(text)
        }
    }

    private fun setHint(textInputLayout: TextInputLayout, hint: String) {
        textInputLayout.hint = hint
    }

    private fun setupDoneButton(updateAction: () -> Unit) {
        binding.imgViewDoneIcon.setOnClickListener {
            updateAction()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenUserInfoState(view)
    }

    private fun listenUserInfoState(view: View) {
        lifecycleScope.launch {
            profileViewModel.userInfoState.collectLatest { userInfoState ->
                when {
                    userInfoState.userInfo != null -> {
                        val action =
                            ProfileEditFragmentDirections.navigateProfileEditFragmentToProfileFragment(
                                user
                            )
                        Navigation.findNavController(view).navigate(action)
                    }
                    userInfoState.error.isNullOrEmpty().not() -> {
                        Log.d("failure", "failure ${userInfoState.error}")
                    }
                }
            }
        }
    }

    private fun updateUser(userId: Int, userInfoModel: UserInfoModel) {
        profileViewModel.updateUserBody(userId, userInfoModel)
    }
}
