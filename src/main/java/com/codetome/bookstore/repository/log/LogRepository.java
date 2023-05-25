package com.codetome.bookstore.repository.log;

import com.codetome.bookstore.domain.Log;
import com.codetome.bookstore.dto.LogDto;

import java.io.IOException;
import java.util.List;

public interface LogRepository {
    List<Log> getAllLogs();
    Number saveNewLog(LogDto log) throws IOException;
}
