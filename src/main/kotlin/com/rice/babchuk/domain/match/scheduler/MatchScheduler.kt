package com.rice.babchuk.domain.match.scheduler

import com.rice.babchuk.domain.match.domain.enum.MatchStatus
import com.rice.babchuk.domain.match.repository.MatchRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MatchScheduler(
    private val matchRepository: MatchRepository,
) {
    private val log = LoggerFactory.getLogger(MatchScheduler::class.java)

    @Scheduled(fixedDelay = 10000)
    @Transactional
    fun updateFinishedMatches() {
        val now = LocalDateTime.now()
        log.info("스케줄러 실행: $now")

        val targets = matchRepository.findAllNotFinishedAndMatchAtBefore(now)

        log.info("종료 처리할 매치 수: ${targets.size}")

        targets.forEach { match ->
            val endTime = match.matchAt.plusMinutes(match.durationMinutes.toLong())

            if (now.isAfter(endTime)) {
                match.status = MatchStatus.FINISHED
                matchRepository.save(match)
                log.info("매치 종료 처리: matchId=${match.id}")
            }
        }
    }
}