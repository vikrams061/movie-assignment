package exception

import java.lang.Exception

data class OmdbErrorException(override val message: String) : Exception()
