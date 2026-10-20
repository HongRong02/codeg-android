package app.codeg.android.feature.settings.customskills

import app.codeg.android.core.model.AgentSkillItem
import app.codeg.android.core.model.ExpertLinkState

fun canEnableCustomSkill(state: ExpertLinkState?): Boolean =
    state != ExpertLinkState.LINKED_ELSEWHERE && state != ExpertLinkState.BLOCKED_BY_REAL_DIRECTORY

fun importableCustomSkills(skills: List<AgentSkillItem>, libraryIds: Set<String>): List<AgentSkillItem> =
    skills.filter { it.scope == "global" && !it.readOnly && it.id !in libraryIds }.distinctBy { it.id }
