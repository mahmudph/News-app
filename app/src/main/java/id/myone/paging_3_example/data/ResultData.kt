package id.myone.paging_3_example.data

sealed class ResultData<T>(
    val data: T? = null,
    val message: String? = null,
    val errorFormField: Map<String, List<String>>? = null
) {
    class Success<T>(data: T) : ResultData<T>(data)
    class Error<T>(
        errorFormField: Map<String, List<String>>? = null,
        errorMessage: String? = null
    ) : ResultData<T>(
        message = errorMessage,
        errorFormField = errorFormField,
    )
}
