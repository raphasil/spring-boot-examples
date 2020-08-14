package com.raphasil.example.async.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class SchedulingService {

  private static final Logger LOG = LoggerFactory.getLogger(SchedulingService.class);
  private final ReadWriteLock lock;
  private final DateTimeFormatter formatter;
  private LocalDateTime localDate;

  public SchedulingService() {
    this.localDate = LocalDateTime.now();
    this.lock = new ReentrantReadWriteLock();
    this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  }

  @Scheduled(fixedDelay = 5000)
  public void schedule() {
    LOG.trace("BEGIN");

    lock.writeLock().lock();
    localDate = LocalDateTime.now();
    lock.writeLock().unlock();

    LOG.info("date update {}", formatter.format(localDate));

    LOG.trace("END");
  }

  public String getLastUpdateDate() {
    lock.readLock().lock();
    final String result = formatter.format(localDate);
    lock.readLock().unlock();
    return result;
  }

}
