package com.teqie.taskmaster.domain.util

import com.teqie.taskmaster.domain.model.teamMember.TeamMember


fun List<TeamMember>.getUserIdByUsername(username: String): String? {
    return this.find { it.username == username }?.id
}