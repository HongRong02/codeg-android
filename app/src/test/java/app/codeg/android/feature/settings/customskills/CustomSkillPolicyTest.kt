package app.codeg.android.feature.settings.customskills

import app.codeg.android.core.model.AgentSkillItem
import app.codeg.android.core.model.ExpertLinkState
import org.junit.Assert.*
import org.junit.Test

class CustomSkillPolicyTest {
    @Test fun `foreign links and real directories cannot be overwritten`() {
        assertFalse(canEnableCustomSkill(ExpertLinkState.LINKED_ELSEWHERE))
        assertFalse(canEnableCustomSkill(ExpertLinkState.BLOCKED_BY_REAL_DIRECTORY))
        assertTrue(canEnableCustomSkill(ExpertLinkState.BROKEN))
        assertTrue(canEnableCustomSkill(null))
    }

    @Test fun `import excludes project read only and already stored skills`() {
        val skills = listOf(
            AgentSkillItem("own"), AgentSkillItem("stored"),
            AgentSkillItem("project", scope = "project"),
            AgentSkillItem("builtin", readOnly = true), AgentSkillItem("own"),
        )
        assertEquals(listOf("own"), importableCustomSkills(skills, setOf("stored")).map { it.id })
    }
}
