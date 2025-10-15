package com.example.Ex02.controller;

import com.example.Ex02.dto.*;
import com.example.Ex02.mapper.MemberMapper;
import com.example.Ex02.mapper.OrderDetailsMapper;
import com.example.Ex02.mapper.OrderMapper;
import com.example.Ex02.mapper.ProductsMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MallController {

    @Autowired private ProductsMapper productsMapper;
    @Autowired private OrderMapper orderMapper;
    @Autowired private OrderDetailsMapper orderDetailsMapper;
    @Autowired private MemberMapper memberMapper;

    /** 장바구니 담기 */
    @PostMapping(value = "/add.mall")
    public String add(ProductsDto pDto, HttpSession session,
                      @RequestParam(value = "whatColumn", required = false) String whatColumn,
                      @RequestParam(value = "keyword", required = false) String keyword,
                      @RequestParam(value = "page", defaultValue = "1") int page) {

        MemberDto loginInfo = (MemberDto) session.getAttribute("loginInfo");

        // 로그인 안 한 경우 → 로그인 페이지로 이동
        if (loginInfo == null) {
            String encodeKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            session.setAttribute("destination",
                    "redirect:/pview.prd?num=" + pDto.getNum()
                            + "&whatColumn=" + whatColumn
                            + "&keyword=" + encodeKeyword
                            + "&page=" + page);
            return "redirect:/login.mb";
        }

        // 로그인 한 경우 → 장바구니 처리
        MyCartList mycart = (MyCartList) session.getAttribute("mycart");
        if (mycart == null) {
            mycart = new MyCartList();
        }
        mycart.addOrder(pDto.getNum(), pDto.getOrderqty());
        session.setAttribute("mycart", mycart);

        return "redirect:/list.mall";
    }

    /** 장바구니 목록 */
    @GetMapping(value = "/list.mall")
    public String list(HttpSession session, Model model) {
        MyCartList mycart = (MyCartList) session.getAttribute("mycart");

        // 장바구니 비어있으면 빈 화면
        if (mycart == null) {
            model.addAttribute("shopLists", new ArrayList<ShoppingInfo>());
            model.addAttribute("totalAmount", 0);
            return "mall/mallList";
        }

        Map<Integer, Integer> maplists = mycart.getAllOrderLists();
        List<ShoppingInfo> shopLists = new ArrayList<>();
        int totalAmount = 0;

        for (Integer pnum : maplists.keySet()) {
            int qty = maplists.get(pnum);
            ProductsDto pDto = productsMapper.findByNum(pnum);

            ShoppingInfo info = new ShoppingInfo();
            info.setPnum(pnum);
            info.setPname(pDto.getName());
            info.setQty(qty);
            info.setPrice(pDto.getPrice());
            info.setAmount(pDto.getPrice() * qty);

            totalAmount += info.getAmount();
            shopLists.add(info);
        }

        model.addAttribute("shopLists", shopLists);
        model.addAttribute("totalAmount", totalAmount);
        return "mall/mallList";
    }

    /** 결제하기 */
    @GetMapping(value = "/calculate.mall")
    public String calculate(HttpSession session) {
        MyCartList mycart = (MyCartList) session.getAttribute("mycart");
        if (mycart == null) return "redirect:/plist.prd";

        Map<Integer, Integer> maplists = mycart.getAllOrderLists();
        MemberDto member = (MemberDto) session.getAttribute("loginInfo");

        // 1. 주문 저장
        OrderDto oDto = new OrderDto();
        oDto.setMid(member.getId());
        orderMapper.insertOrder(oDto);

        int maxOid = orderMapper.getMaxOid();

        // 2. 주문 상세 저장 + 재고 감소
        for (Integer pnum : maplists.keySet()) {
            int qty = maplists.get(pnum);

            OrderDetailDto odDto = new OrderDetailDto();
            odDto.setOid(maxOid);
            odDto.setPnum(pnum);
            odDto.setQty(qty);
            orderDetailsMapper.insertOrderDetails(odDto);

            ProductsDto pDto = new ProductsDto();
            pDto.setNum(pnum);
            pDto.setStock(qty); // 차감할 수량
            productsMapper.updateStock(pDto);
        }

        // 3. 포인트 적립 (100점 추가)
        member.setMpoint(100);
        memberMapper.updateMpoint(member);

        // 4. 장바구니 비우기
        session.removeAttribute("mycart");

        return "redirect:/plist.prd";
    }

    /** 나의 주문 내역 */
    @GetMapping(value = "/order.mall")
    public String order(HttpSession session, Model model) {
        MemberDto loginInfo = (MemberDto) session.getAttribute("loginInfo");

        if (loginInfo == null) {
            session.setAttribute("destination", "redirect:/order.mall");
            return "redirect:/login.mb";
        }

        List<OrderDto> lists = orderMapper.orderMall(loginInfo);
        model.addAttribute("lists", lists);

        return "mall/shopList";
    }

    /** 주문 상세보기 */
    @GetMapping(value = "/detailView.mall")
    public String detailView(@RequestParam("oid") int oid, HttpSession session, Model model) {
        MemberDto loginInfo = (MemberDto) session.getAttribute("loginInfo");

        if (loginInfo == null) {
            session.setAttribute("destination", "redirect:/detailView.mall?oid=" + oid);
            return "redirect:/login.mb";
        }

        List<ShoppingInfo> lists = orderMapper.showDetail(oid);
        model.addAttribute("lists", lists);
        model.addAttribute("oid", oid);

        return "mall/shopResult";
    }
}
