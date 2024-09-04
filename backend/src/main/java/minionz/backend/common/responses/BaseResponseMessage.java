package minionz.backend.common.responses;

public enum BaseResponseMessage {
    // ========================================================================================================================
    // 요청 성공, 실패, 내부 서버 오류
    REQUEST_SUCCESS(true, 200, "요청이 정상적으로 처리되었습니다"),
    REQUEST_FAIL(false, 404, "요청을 실패했습니다."),
    INTERNAL_SERVER_ERROR(false, 500, "내부 서버 오류"),
    FILE_UPLOAD_FAIL(false, 501, "파입 업로드에 실패했습니다."),
    // ========================================================================================================================
    // 회원 기능(2000)
    // 회원가입 2000
    MEMBER_REGISTER_SUCCESS(true, 2000, "이메일 인증을 완료해주세요"),
    MEMBER_REGISTER_FAIL(false, 2001, "회원가입에 실패했습니다."),
    MEMBER_REGISTER_FAIL_ALREADY_REGISTER_AS_CUSTOMER(false, 2002, "이미 고객 회원으로 가입된 계정입니다. 고객 회원은 기업 회원으로 회원가입 할 수 없습니다."),
    MEMBER_REGISTER_FAIL_ALREADY_REGISTER_AS_COMPANY(false, 2003, "이미 기업 회원으로 가입된 계정입니다. 기업 회원은 고객 회원으로 회원가입할 수 없습니다."),
    MEMBER_REGISTER_FAIL_ID_EMPTY(false, 2002, "아이디를 입력해주세요"),
    MEMBER_REGISTER_FAIL_ID_FORMAT(false, 2003, "아이디 형식이 맞지 않습니다."),
    MEMBER_REGISTER_FAIL_ID_DUPLICATION(false, 2004, "아이디가 중복되었습니다."),
    MEMBER_REGISTER_FAIL_PASSWORD_EMPTY(false, 2005, "패스워드를 입력해주세요"),
    MEMBER_REGISTER_FAIL_PASSWORD_FORMAT(false, 2006, "패스워드 형식이 맞지 않습니다."),
    MEMBER_REGISTER_FAIL_PASSWORD_COMPLEXITY(false, 2007, "복잡한 패스워드를 사용해주세요"),
    MEMBER_REGISTER_FAIL_NAME_EMPTY(false, 2008, "이름을 입력해주세요"),
    MEMBER_REGISTER_FAIL_ALREADY_EXIST(false, 2009, "이미 회원가입한 계정입니다."),
    // 이메일 인증 2010
    MEMBER_EMAIL_VERIFY_SUCCESS(true, 2010, "이메일 인증을 완료했습니다."),
    MEMBER_EMAIL_VERIFY_FAIL(false, 2011, "이메일 인증에 실패했습니다."),
    MEMBER_EMAIL_SEND_FAIL(false, 2012, "이메일 전송에 실패했습니다."),
    MEMBER_EMAIL_VERIFY_SAVE_FAIL(false, 2013, "이메일 인증 값 저장에 실패했습니다."),
    // 엑세스 토큰 2020
    MEMBER_ACCESS_TOKEN_EXPIRED(false, 2021, "JWT 토큰이 만료되었습니다."),
    MEMBER_TOKEN_NULL(false, 2022, "JWT 토큰이 없습니다."),
    MEMBER_ACCESS_TOKEN_INVALID(false, 2023, "JWT 토큰이 유효하지 않습니다."),
    // 로그인 2030
    MEMBER_LOGIN_SUCCESS(true, 2030, "로그인에 성공했습니다."),
    MEMBER_LOGIN_FAIL(false, 2031, "아이디 또는 비밀번호를 확인해주세요"),
    MEMBER_LOGIN_FAIL_NOT_FOUND(false, 2032, "사용자를 찾을 수 없습니다"),
    // 계정 비활성화 2040
    MEMBER_INACTIVE_SUCCESS(true, 2040, "계정 비활성화에 성공했습니다."),
    MEMBER_INACTIVE_FAIL(false, 2041, "계정 비활성화에 실패했습니다."),
    // 계정 정보 수정 2050
    MEMBER_EDIT_INFO_SUCCESS(true, 2050, "계정 프로필 정보 변경에 성공했습니다."),
    MEMBER_EDIT_INFO_FAIL(false, 2051, "계정 프로필 정보 변경에 실패했습니다."),

    // 계정 패스워드 수정 2060
    MEMBER_EDIT_PASSWORD_SUCCESS(true, 2050, "계정 비밀번호 변경에 성공했습니다."),
    MEMBER_EDIT_PASSWORD_FAIL(false, 2051, "계정 비밀번호 변경에 실패했습니다."),
    MEMBER_EDIT_PASSWORD_FAIL_PASSWORD_NOT_MATCH(false, 2052, "계정 비밃번호가 틀립니다."),
    // 프로필 정보 2070
    MEMBER_PROFILE_SUCCESS(true,2070,"프로필 조회에 성공했습니다"),
    MEMBER_PROFILE_FAIL(true,2071,"프로필 조회에 실패했습니다"),
    // 멤버 로그아웃 2080
    MEMBER_LOGOUT_SUCCESS(true, 2080, "로그아웃에 성공했습니다."),
    // ========================================================================================================================
    // 팝업 스토어 3000
    // 팝업 스토어 등록 3000
    POPUP_STORE_REGISTER_SUCCESS(true, 3000, "팝업 스토어 등록에 성공했습니다."),
    POPUP_STORE_REGISTER_FAIL_DUPLICATION(false, 3001, "이미 등록된 팝업 스토어 입니다."),
    POPUP_STORE_REGISTER_FAIL_UNAUTHORIZED(false, 3002, "팝업 스토어 등록은 기업회원만 가능합니다."),
    // 팝업 스토어 조회 3100
    POPUP_STORE_SEARCH_SUCCESS(true, 3100, "팝업 스토어 목록 조회에 성공했습니다."),
    POPUP_STORE_SEARCH_FAIL_NOT_EXIST(false, 3101, "해당 팝업 스토어가 존재하지 않습니다."),
    // 팝업 스토어 수정 3200
    POPUP_STORE_UPDATE_SUCCESS(true, 3200, "팝업 스토어 수정에 성공했습니다."),
    POPUP_STORE_UPDATE_FAIL_INVALID_MEMBER(false, 3201, "해당 팝업스토어를 등록한 기업회원이 아닙니다."),
    // 팝업 스토어 삭제 3300
    POPUP_STORE_DELETE_SUCCESS(true, 3300, "팝업 스토어 삭제에 성공했습니다."),
    POPUP_STORE_DELETE_FAIL_NOT_FOUND(false, 3301, "팝업 스토어를 찾을 수 없어 삭제에 실패했습니다."),
    POPUP_STORE_DELETE_FAIL_INVALID_MEMBER(false, 3302, "해당 팝업 스토어를 등록한 기업 회원이 아닙니다."),
    // 팝업 스토어 추천 3400
    POPUP_STORE_LIKE_SUCCESS(true, 3400, "팝업 스토어 좋아요 성공"),
    POPUP_STORE_LIKE_FAIL_NOT_FOUND(false, 3400, "해당 팝업 스토어를 찾을 수 없습니다."),
    POPUP_STORE_LIKE_FAIL_INVALID_MEMBER(false, 3401, "인증된 사용자 만이 추천을 누를 수 있습니다."),


    // ========================================================================================================================
    // 팝업 스토어 리뷰 4000
    // 팝업 스토어 리뷰 등록 4000
    POPUP_STORE_REVIEW_SUCCESS(true, 4000, "팝업 스토어 리뷰 등록에 성공했습니다."),
    POPUP_STORE_REVIEW_FAIL_CONTENTS_EMPTY(false, 4001, "팝업 스토어 리뷰 내용을 작성해주세요"),
    POPUP_STORE_REVIEW_FAIL_STORE_NOT_EXIST(false, 4002, "해당 팝업 스토어를 찾을 수 없습니다."),
    POPUP_STORE_REVIEW_FAIL_INVALID_MEMBER(false, 4003, "인증된 사용자만이 리뷰를 등록할 수 있습니다."),
    // ========================================================================================================================
    // 팝업 예약 9000
    // 팝업 예약 생성 9000
    POPUP_RESERVE_CREATE_SUCCESS(true, 9000, "예약 등록에 성공했습니다."),
    POPUP_RESERVE_CREATE_FAIL_TIME_CLOSED(false, 9001, "해당 시간대는 예약이 마감되었습니다."),
    POPUP_RESERVE_CREATE_FAIL_INVALID_MEMBER(false, 9002, "기업 회원이 아닌 회원은 예약을 생성할 수 없습니다."),
    POPUP_RESERVE_CREATE_FAIL_NOT_FOUND(false, 9003, "예약을 생성하려는 팝업스토어를 찾을 수 없습니다."),
    // 팝업 예약 취소 9100
    POPUP_RESERVE_CANCEL_SUCCESS(true, 9100, "예약 취소에 성공했습니다."),
    POPUP_RESERVE_CANCEL_FAIL(false,  9101, "예약 취소에 실패했습니다."),
    // 팝업 예약 접속 9200
    POPUP_RESERVE_ENROLL_SUCCESS(true, 9200, "예약에 성공했습니다."),
    POPUP_RESERVE_ENROLL_FAIL(false, 9201, "예약에 실패했습니다."),
    // 팝업 예약 조회 9300
    POPUP_RESERVE_SEARCH_STATUS_SUCCESS(true, 9300, "예약 대기자 및 Redis 상태를 불러왔습니다."),

    // ========================================================================================================================
    // 팝업 굿즈 4000
    // 팝업 굿즈 등록 4000
    POPUP_GOODS_REGISTER_SUCCESS(true, 4000,"상품 등록에 성공했습니다."),
    POPUP_GOODS_REGISTER_FAIL(false, 4001, "상품 등록에 실패했습니다."),
    POPUP_GOODS_REGISTER_FAIL_IMG_FORMAT(false, 4002, "상품 이미지 파일 형식이 맞지 않습니다."),
    POPUP_GOODS_REGISTER_FAIL_NAME_EMPTY(false, 4003, "상품명을 입력해주세요"),
    POPUP_GOODS_REGISTER_FAIL_PRICE_EMPTY(false, 4004, "상품 가격을 입력해주세요"),
    POPUP_GOODS_REGISTER_FAIL_AMOUNT_EMPTY(false, 4005, "등록할 상품의 수량을 입력해주세요"),
    POPUP_GOODS_REGISTER_FAIL_STORE_NOT_FOUND(false, 4006, "해당 스토어를 찾을 수 없습니다."),
    // 팝업 굿즈 조회 4100
    POPUP_GOODS_SEARCH_SUCCESS(true, 4100, "팝업 굿즈 조회에 성공했습니다."),
    POPUP_GOODS_SEARCH_FAIL(false, 4101, "팝업 굿즈 조회에 실패했습니다."),
    POPUP_GOODS_SEARCH_FAIL_STORE_NOT_NOT_FOUND(false, 4102, "해당 스토어를 찾을 수 없습니다."),
    // 팝업 굿즈 수정 4200
    POPUP_GOODS_UPDATE_SUCCESS(true, 4200, "팝업 굿즈 수정에 성공했습니다."),
    POPUP_GOODS_UPDATE_FAIL(false, 4201, "팝업 굿즈 수정에 실패했습니다."),
    // 팝업 굿즈 삭제 4300
    POPUP_GOODS_DELETE_SUCCESS(true, 4300, "팝업 굿즈 삭제에 성공했습니다."),
    POPUP_GOODS_DELETE_FAIL(false, 4301, "팝업 굿즈 삭제에 실패했습니다."),
    // 팝업 굿즈 주문 검증 4400


    // 팝업 굿즈 결제 4500


    // 결제
    // 기업 수수료 결제 4500
    POPUP_PAY_SUCCESS(true, 4500,"결제에 성공했습니다."),
    POPUP_PAY_FAIL_NOT_INVALID(false, 4501, "결제에 실패했습니다."),
    // 기업 수수료 결제 4600
    POPUP_STORE_PAY_FAIL(false, 4601, "기업 팝업 수수료 결제에 실패했습니다."),
    POPUP_STORE_PAY_FAIL_NOT_FOUND_PAYINFO(false, 4602, "기업 팝업 수수료 결제 정보를 가져올수 없습니다."),
    POPUP_STORE_PAY_FAIL_NOT_FOUND_COMPANY(false, 4607, "해당 기업 회원은 존재하지 않습니다."),
    POPUP_STORE_PAY_FAIL_INCORRECT_REQUEST(false, 4608, "결제 형식이 다릅니다. 기업 수수료 결제를 진행해주세요"),
    POPUP_STORE_PAY_FAIL_NOT_FOUND_STORE(false, 4409, "해당 팝업 스토어가 존재하지 않습니다"),
    POPUP_STORE_PAY_FAIL_VALIDATION_ERROR(false, 4410, "결제 금액이 잘못 되었습니다."),
    POPUP_STORE_PAY_FAIL_NOT_FOUND_AMOUNT(false, 4408, "결제 금액을 찾을 수 없습니다."),
    POPUP_GOODS_PAY_FAIL_POINT_EXCEEDED(false, 4411, "3000포인트 이상부터 사용할 수 있습니다."),
    // 굿즈 구매 4700
    POPUP_GOODS_PAY_GOODS_NULL(false, 4406, "굿즈가 존재하지 않습니다."),

    POPUP_GOODS_PAY_FAIL_EXCEEDED(false,4405,"해당 상품의 재고가 부족합니다."),
    POPUP_GOODS_PAY_FAIL_LIMIT_EXCEEDED(false, 4407, "사전 예매 굿즈는 품목 당 하나만 구매 가능합니다."),
    POPUP_GOODS_PAY_FAIL_VALIDATION_ERROR(false, 4404, "결제 금액이 잘못되었습니다."),
    POPUP_GOODS_PAY_FAIL_NOT_FOUND_MEMBER(false, 4408, "결제 정보에 해당하는 유저가 없습니다."),
    POPUP_ORDERS_SEARCH_SUCCESS(true, 4409, "결제 내역 조회에 성공했습니다."),
    POPUP_PAY_SEARCH_FAIL_INVALID_MEMBER(false, 4410, "해당 사용자를 찾을 수 없어 결제 내역을 불러올 수 없습니다."),
    POPUP_PAY_SEARCH_FAIL_NOT_FOUND(false, 4411, "결제 내역 조회를 불러올 수 없습니다."),


    // ========================================================================================================================
    // 장바구니 5000
    // 장바구니 등록 5000
    CART_REGISTER_SUCCESS(true, 5000  , "장바구니 등록에 성공했습니다." ),
    CART_REGISTER_FAIL(false, 5001  , "장바구니 등록에 실패했습니다." ),
    CART_REGISTER_FAIL_MEMBER_NOT_FOUND(false, 5002, "장바구니 사용자를 찾을수 없습니다." ),
    CART_REGISTER_FAIL_GOODS_NOT_FOUND(false, 5003, "상품을 찾을 수 없습니다."),
    CART_REGISTER_FAIL_EXIST(false, 5004, "장바구니가 이미 존재 합니다."),
    // 장바구니 조회 5100
    CART_SEARCH_LIST_SUCESS(true, 5100, "장바구니 리스트를 불러왔습니다."),
    CART_SEARCH_FAIL(false, 5101, "장바구니 리스트를 불러오는데 실패했습니다."),
    // 장바구니 수량 조절 5200
    CART_COUNT_SUCCESS(true, 5200, "장바구니 아이템의 수량을 조절에 성공했습니다."),
    CART_COUNT_FAIL_INVALID_OPERATION(false, 5201, "장바구니 아이템의 수량을 감소에 실패했습니다."),
    CART_COUNT_FAIL_IS_0(false, 5202, "장바구니 아이템의 수량이 0입니다."),
    CART_COUNT_FAIL_NOT_FOUND(false, 5203, "장바구니 아이템을 찾을 수 없습니다."),
    // 장바구니 삭제 5300
    CART_DELETE_SUCCESS(true, 5300, "장바구니 아이템 삭제에 성공했습니다."),
    CART_DELETE_FAIL(false, 5301, "장바구니 아이템 삭제에 실패했습니다."),
    CART_DELETE_FAIL_ITEM_NOT_FOUND(false, 5302, "장바구니 아이템을 찾을 수 없습니다."),
    // 장바구니 전체삭제 5400
    CART_DELETE_ALL_SUCCESS(true, 5400, "장바구니 전체 삭제에 성공했습니다."),
    CART_DELETE_ALL_FAIL(false, 5401, "장바구니 전체 삭제에 실패했습니다."),
    //장바구니 포인트 5500
    CART_POINT_SEARCH_SUCCESS(true,5500,"포인트 조회에 성공했습니다."),
    CART_POINT_SEARCH_FAIL(false,5501,"포인트 조회에 실패했습니다"),
    // ========================================================================================================================
    // 찜 6000
    // 찜 등록 6100
    FAVORITE_ACTIVE_SUCCESS(true, 6100, "해당 팝업 스토어를 찜 목록에 추가했습니다."),
    FAVORITE_ACTIVE_FAIL_STORE_NOT_FOUND(false, 6101, "해당 팝업 스토어를 찾을 수 없습니다."),
    FAVORITE_ACTIVE_FAIL_MEMBER_NOT_FOUND(false, 6102, "유저를 찾을 수 없습니다."),
    FAVORITE_INACTIVE_SUCCESS(true, 6200, "해당 팝업 스토어를 찜 목록에서 뺏습니다."),
    // 찜 조회 6200
    FAVORITE_SEARCH_ALL_SUCCESS(true, 6200, "팝업 스토어 찜 목록을 불러오는데 성공했습니다."),
    FAVORITE_SEARCH_ALL_FAIL(false, 6201, "팝업 스토어 찜 목록을 불러오는데 실패했습니다."),

    // ========================================================================================================================


    // 게시글 6000 ========================================================================================================================
    // 게시글 등록 6000
    POST_REGISTER_SUCCESS(true, 6000, "게시글이 성공적으로 등록되었습니다."),
    POST_REGISTER_FAIL(false, 6001, "게시글 등록에 실패했습니다."),
    POST_REGISTER_FAIL_INVALID_MEMBER(false, 6002, "인증된 사용자만이 게시글을 등록할 수 있습니다."),
    // 게시글 전체 조회 6100
    POST_SEARCH_ALL_SUCCESS(true, 6100, "전체 게시글을 불러오는데 성공했습니다." ),
    POST_SEARCH_ALL_FAIL(false, 6101, "전체 게시글을 불러오는데 실패했습니다."),

    // 게시글 단일 조회 6200
    POST_SEARCH_BY_IDX_SUCCESS(true, 6200, "단일 게시글을 불러오는데 성공했습니다."),
    POST_SEARCH_BY_IDX_FAIL(false, 6201, "단일 게시글을 불러오는데 실패했습니다."),

    // 게시글 검색어 추천 조회 6300
    POST_SEARCH_BY_KEYWORD_SUCCESS(true, 6300, "게시글 검색어 추천 조회에 성공했습니다."),
    POST_SEARCH_FAIL(false, 6301, "게시글 검색어 추천 조회에 실패했습니다."),

    // 게시글 고객 회원 조회 6400
    POST_SEARCH_BY_CUSTOMER_SUCCESS(false, 6400, "사용자가 작성한 게시글을 불러왔습니다."),
    POST_SEARCH_BY_CUSTOMER_FAIL(false, 6401, "사용자가 작성한 게시글을 불러오는데 실패했습니다."),

    // 게시글 고객 회원 수정 6500
    POST_UPDATE_SUCCESS(true, 6500, "게시글 수정에 성공했습니다."),
    POST_UPDATE_FAIL(false, 6501, "게시글 수정에 실패했습니다."),
    POST_UPDATE_FAIL_INVALID_MEMBER(false, 6502, "해당 게시글의 작성자가 아닙니다." ),
    POST_UPDATE_FAIL_NOT_FOUND_IMAGE(false, 6503, ""),
    POST_UPDATE_FAIL_NOT_FOUND_POST(false, 6504, "해당 게시글을 찾을 수 없습니다."),
    // 게시글 고객 회원 삭제 6600
    POST_DELETE_SUCCESS(true, 6600, "게시글 삭제에 성공했습니다."),
    POST_DELETE_FAIL_INVALID_MEMBER(false, 6601, "해당 게시글의 작성자가 아닙니다."),
    POST_DELETE_FAIL_POST_NOT_FOUND(false, 6602, "해당 게시글을 찾을 수 없습니다."),
    POST_DELETE_FAIL(false, 6602, "게시글 삭제에 실패했습니다."),

    // 게시글 추천 6700
    POST_LIKE_SUCCESS(true, 6700, "게시글 추천에 성공했습니다."),
    POST_LIKE_FAIL_POST_NOT_FOUND(false, 6701, "해당 게시글을 찾을 수 없습니다."),
    POST_LIKE_FAIL_INVALID_MEMBER(false, 6702, "인증된 사용자 만이 게시글을 추천할 수 있습니다."),

    // 댓글 7000 ========================================================================================================================
    // 댓글 생성 7000
    COMMENT_REGISTER_SUCCESS(true, 7000, "댓글이 성공적으로 등록되었습니다."),
    COMMENT_REGISTER_FAIL(false, 7001, "댓글 등록에 실패했습니다."),
    COMMENT_REGISTER_FAIL_INVALID_MEMBER(false, 7003, "로그인한 사용자만이 댓글을 등록할수있습니다."),
    COMMENT_REGISTER_FAIL_POST_NOT_FOUND(false,7003,"해당 게시글을 찾을 수 없습니다."),

    // 댓글 전체 조회 7100
    COMMENT_SEARCH_ALL_SUCCESS(true, 7100, "전체 댓글을 불러왔습니다." ),
    COMMENT_SEARCH_ALL_FAIL(false, 7101, "전체 댓글을 불러오는데 실패했습니다."),

    // 댓글 고객 회원 조회 7200
    COMMENT_SEARCH_BY_CUSTOMER_SUCCESS(true, 7200, "사용자의 전체 댓글을 불러왔습니다. " ),
    COMMENT_SEARCH_BY_CUSTOMER_FAIL(false, 7201, "사용자의 전체 댓글을 불러오는데 실패했습니다."),

    // 댓글 수정 7300
    COMMENT_UPDATE_SUCCESS(true, 7300, "댓글을 수정하는데 성공했습니다."),
    COMMENT_UPDATE_FAIL_INVALID_MEMBER(false, 7301, "해당 댓글을 단 사용자가 아닙니다."),
    COMMENT_UPDATE_FAIL_COMMENT_NOT_FOUND(false, 7302, "해당 댓글을 찾을 수 없습니다."),

    // 댓글 삭제 7400
    COMMENT_DELETE_SUCCESS(true, 7400, "댓글을 삭제하는데 성공했습니다."),
    COMMENT_DELETE_FAIL_INVALID_MEMBER(false, 7401, "해당 댓글을 단 사용자가 아닙니다."),
    COMMENT_DELETE_FAIL_COMMENT_NOT_FOUND(false, 7402, "해당 댓글을 찾을 수 없습니다."),

    // 댓글 추천 7500
    COMMENT_LIKE_SUCCESS(true, 7500, "댓글 추천에 성공했습니다."),
    COMMENT_LIKE_FAIL_INVALID_MEMBER(false, 7501,"인증된 사용자만이 댓글을 추천할 수 있습니다."),
    COMMENT_LIKE_FAIL_COMMENT_NOT_FOUND(false, 7502,"해당 댓글을 찾을 수 없습니다."),


    // ========================================================================================================================
    // 채팅 8000
    // 채팅방 생성 8000
    CHAT_ROOM_CREATE_SUCCESS(true, 8000, "채팅방 생성에 성공했습니다."),
    CHAT_ROOM_CREATE_FAIL(false, 8001, "채팅방 생성에 실패했습니다."),
    // 채팅방 참여 8100
    CHAT_ROOM_JOIN_SUCCESS(true, 8100, "채팅방에 성공적으로 참여했습니다."),
    CHAT_ROOM_JOIN_FAIL(false, 8101, "채팅방 참여에 실패했습니다."),
    // 채팅방 나가기 8200
    CHAT_ROOM_LEAVE_SUCCESS(true, 8200, "채팅방에서 성공적으로 나갔습니다."),
    CHAT_ROOM_LEAVE_FAIL(false, 8201, "채팅방 나가기에 실패했습니다."),
    // 채팅방 사용자 조회 8300
    CHAT_USER_COUNT_SEARCH_SUCCESS(true, 8300, "채팅방 사용자 수 조회에 성공했습니다."),
    CHAT_USER_COUNT_SEARCH_FAIL(false, 8301, "채팅방 사용자 수 조회에 실패했습니다."),

    // 채팅방 기록 조회 8400
    CHAT_HISTORY_SEARCH_SUCCESS(true, 8500, "채팅 기록 조회에 성공했습니다."),
    CHAT_HISTORY_SEARCH_FAIL(false, 8501, "채팅 기록 조회에 실패했습니다."),
    // 채팅방 조회 8500
    CHAT_ROOM_SEARCH_SUCCESS(true, 8600, "채팅방 목록 조회에 성공했습니다."),
    CHAT_ROOM_SEARCH_FAIL(false, 8601, "채팅 기록 조회에 실패했습니다."),
    // 채팅 메세지 전송 8400
    CHAT_MESSAGE_SEND_SUCCESS(true, 8300, "채팅 메시지 전송에 성공했습니다."),
    CHAT_MESSAGE_SEND_FAIL(false, 8301, "채팅 메시지 전송에 실패했습니다."),

    //채팅 유저 확인 8500
    CHAT_USER_FOUND(true,8500,"사용자가 존재합니다."),
    USER_NOT_FOUND(false,8401 ,"사용자가 존재하지 않습니다" );

    // ========================================================================================================================
    private Boolean success;
    private Integer code;
    private String message;

    BaseResponseMessage(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
    public static BaseResponseMessage findByCode(Integer code) {
        for (BaseResponseMessage message : values()) { if (message.getCode().equals(code)) { return message; }}
        return null;
    }
    public Boolean getSuccess() { return success; }

    public Integer getCode() { return code; }

    public String getMessage() { return message; }
}