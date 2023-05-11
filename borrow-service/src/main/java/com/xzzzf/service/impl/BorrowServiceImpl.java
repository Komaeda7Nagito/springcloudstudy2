package com.xzzzf.service.impl;

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
    public BorrowDetails getUserBorrowDetails(Integer uid) {
        List<Borrow> borrows = borrowMapper.getBorrowsByUid(uid);
        User user = userClient.getUserById(uid);

        List<Book> bookList = borrows
                .stream()
                .map(borrow -> bookClient.findBookById(borrow.getBid()))
                .collect(Collectors.toList());
        return new BorrowDetails(user, bookList);
    }


}
