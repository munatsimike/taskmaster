package com.teqie.taskmaster.ui.model

sealed class BudgetFormError(val key: String) {
    data object EmptyPhase : BudgetFormError("phase")
    data object EmptyBudget : BudgetFormError("budget")
    data object EmptyUser : BudgetFormError("user")
    data object InvalidTotalDuration : BudgetFormError("totalDuration")
    data object InvalidProgress : BudgetFormError("progress")
}