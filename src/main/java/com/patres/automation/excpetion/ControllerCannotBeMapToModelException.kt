package com.patres.automation.excpetion

import com.patres.automation.type.ActionBootable

class ControllerCannotBeMapToModelException(val action: ActionBootable) : ApplicationException("Controller $action with bundle name: ${action.bundleName()} cannot be map to model")
