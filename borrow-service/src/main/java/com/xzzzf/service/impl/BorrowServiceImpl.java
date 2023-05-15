package com.xzzzf.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xzzzf.entity.Book;
import com.xzzzf.entity.Borrow;
import com.xzzzf.entity.BorrowDetails;
import com.xzzzf.entity.User;
import com.xzzzf.mapper.BorrowMapper;
import com.xzzzf.service.BorrowService;
import com.xzzzf.service.client.BookClient;
import com.xzzzf.service.client.UserClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BorrowServiceImpl implements BorrowService {

    @Resource
    private BorrowMapper borrowMapper;

    @Resource
    private UserClient userClient;

    @Resource
    private BookClient bookClient;

    @Override
    @SentinelResource(value = "getUserBorrowDetails", blockHandler = "blocked")
    public BorrowDetails getUserBorrowDetails(Integer uid) {
        List<Borrow> borrows = borrowMapper.getBorrowsByUid(uid);
        User user = userClient.getUserById(uid);

        List<Book> bookList = borrows
                .stream()
                .map(borrow -> bookClient.findBookById(borrow.getBid()))
                .collect(Collectors.toList());
        return new BorrowDetails(user, bookList);
    }

    /**
     * 限流处理
     * @param uid
     * @param exception
     * @return
     */
    public BorrowDetails blocked(Integer uid, BlockException exception) {
        return new BorrowDetails(null, Collections.emptyList());
    }


}
