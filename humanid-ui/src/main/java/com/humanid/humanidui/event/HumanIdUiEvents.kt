package com.humanid.humanidui.event

class CloseAllActivityEvent(val exchangeToken: String?, val errorMessage: String?, var isCancel: Boolean = false)