package com.muratdayan.epasaj.data.remote.mapper

import com.muratdayan.epasaj.data.remote.dto.AddressDto
import com.muratdayan.epasaj.data.remote.dto.BankDto
import com.muratdayan.epasaj.data.remote.dto.CartAddProductResponseDto
import com.muratdayan.epasaj.data.remote.dto.CartAddResponseDto
import com.muratdayan.epasaj.data.remote.dto.CartDto
import com.muratdayan.epasaj.data.remote.dto.CartProductDto
import com.muratdayan.epasaj.data.remote.dto.CategoriesItemDto
import com.muratdayan.epasaj.data.remote.dto.CompanyDto
import com.muratdayan.epasaj.data.remote.dto.MetaDto
import com.muratdayan.epasaj.data.remote.dto.ProductDto
import com.muratdayan.epasaj.data.remote.dto.ProductResponseDto
import com.muratdayan.epasaj.data.remote.dto.ReviewDto
import com.muratdayan.epasaj.data.remote.dto.UserInfoResponseDto
import com.muratdayan.epasaj.data.remote.dto.UserResponseDto
import com.muratdayan.epasaj.domain.model.network_models.response_models.CartAddModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.CartModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.CartProductModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.CategoriesItemModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.MetaModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductAddModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductListModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.ProductModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.ReviewModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoAddressModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoBankModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoCompanyModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserInfoModel
import com.muratdayan.epasaj.domain.model.network_models.response_models.UserModel

// dto nesnelerinin modellere dönüştürülmesi

fun ReviewDto.toReviewModel(): ReviewModel {
    return ReviewModel(
        comment = comment,
        date = date,
        rating = rating,
        reviewerEmail = reviewerEmail,
        reviewerName = reviewerName
    )
}

fun MetaDto.toMetaModel(): MetaModel {
    return MetaModel(
        qrCode = qrCode,
    )
}

fun ProductDto.toProductModel(): ProductModel {
    return ProductModel(
        id = id,
        title = title,
        description = description,
        tags = tags,
        sku = sku,
        shippingInformation = shippingInformation,
        warrantyInformation = warrantyInformation,
        minimumOrderQuantity = minimumOrderQuantity,
        brand = brand,
        price = price,
        stock = stock,
        availabilityStatus = availabilityStatus,
        images = images,
        rating = rating,
        discountPercentage = discountPercentage,
        weight = weight,
        category = category,
        thumbnail = thumbnail,
        returnPolicy = returnPolicy,
        reviews = reviews.map { it.toReviewModel() },
        meta = meta.toMetaModel()
    )
}

fun ProductResponseDto.toProductListModel(): ProductListModel {
    return ProductListModel(
        productList = products.map { it.toProductModel() }
    )
}

fun CategoriesItemDto.toCategoriesItemModel(): CategoriesItemModel {
    return CategoriesItemModel(
        categoryName = name,
        categorySlug = slug
    )
}

fun UserResponseDto.toUserModel(): UserModel {
    return UserModel(
        email = email,
        firstName = firstName,
        gender = gender,
        id = id,
        image = image,
        lastName = lastName,
        refreshToken = refreshToken,
        token = token,
        username = username
    )
}

fun CartProductDto.toCartProductModel(): CartProductModel {
    return CartProductModel(
        discountPercentage = discountPercentage,
        discountedTotal = discountedTotal,
        id = id,
        price = price,
        quantity = quantity,
        thumbnail = thumbnail,
        title = title,
        total = total
    )
}

fun CartDto.toCartModel(): CartModel {
    return CartModel(
        discountedTotal = discountedTotal,
        id = id,
        products = products.map { it.toCartProductModel() },
        total = total,
        totalProducts = totalProducts,
        totalQuantity = totalQuantity,
        userId = userId
    )
}

fun AddressDto.toUserInfoAddressModel(): UserInfoAddressModel {
    return UserInfoAddressModel(
        address = address,
        city = city,
        country = country,
        state = state
    )
}

fun CompanyDto.toUserInfoCompanyModel(): UserInfoCompanyModel {
    return UserInfoCompanyModel(
        address = address.toUserInfoAddressModel(),
        department = department,
        name = name,
        title = title
    )
}

fun BankDto.toBankModel(): UserInfoBankModel {
    return UserInfoBankModel(
        cardExpire = cardExpire,
        cardNumber = cardNumber,
        cardType = cardType,
        currency = currency,
        iban = iban
    )
}

fun UserInfoResponseDto.toUserInfoModel(): UserInfoModel {
    return UserInfoModel(
        weight = weight,
        height = height,
        eyeColor = eyeColor,
        email = email,
        firstName = firstName,
        gender = gender,
        lastName = lastName,
        username = username,
        password = password,
        phone = phone,
        role = role,
        ssn = ssn,
        university = university,
        userAgent = userAgent,
        id = id,
        image = image,
        ip = ip,
        macAddress = macAddress,
        maidenName = maidenName,
        birthDate = birthDate,
        bloodGroup = bloodGroup,
        ein = ein,
        age = age,
        address = address.toUserInfoAddressModel(),
        company = company.toUserInfoCompanyModel(),
        bank = bank.toBankModel(),
    )
}


fun CartAddProductResponseDto.toProductAddModel(): ProductAddModel {
    return ProductAddModel(
        discountPercentage = discountPercentage,
        discountedPrice = discountedPrice,
        id = id,
        price = price,
        quantity = quantity,
        thumbnail = thumbnail,
        title = title,
        total = total
    )
}

fun CartAddResponseDto.toCartAddModel(): CartAddModel {
    return CartAddModel(
        discountedTotal = discountedTotal,
        id = id,
        products = products.map { it.toProductAddModel() },
        total = total,
        totalProducts = totalProducts,
        totalQuantity = totalQuantity,
        userId = userId
    )
}