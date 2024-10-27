package minionz.apiserver.common.responses;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : 공통 에러
     */
    FILE_UPLOAD_FAIL(false, 1001, "파일 업로드에 실패했습니다."),
    INVALID_ACCESS(false, 1002, "비정상적인 접근입니다."),
    INVALID_PARAMETER(false, 1003, "잘못된 파라미터입니다."),
    DATABASE_CONNECTION_FAIL(false, 1004, "데이터베이스 연결에 실패했습니다."),
    UNAUTHORIZED_ACCESS(false, 1005, "권한이 없는 접근입니다."),
    RESOURCE_NOT_FOUND(false, 1006, "요청한 리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(false, 1007, "서버 내부 오류입니다."),

    /**
     * 2000: 유저
     */
    USER_CREATE_SUCCESS(true, 2001, "회원가입에 성공했습니다. "),
    USER_LOGIN_SUCCESS(true, 2002, "로그인에 성공했습니다. "),
    USER_ID_NOT_DUPLICATE(true, 2003, "사용 가능한 아이디 입니다. "),
    USER_ID_DUPLICATE(false, 2004, "중복된 아이디 입니다. "),
    USER_EMAIL_NOT_DUPLICATE(true, 2005, "사용가능한 이메일 입니다. "),
    USER_EMAIL_DUPLICATE(false, 2006, "중복된 이메일 입니다. "),
    USER_UUID_VALID(true, 2007, "유효한 인증 코드입니다. "),
    USER_UUID_INVALID(false, 2008, "유효하지 않은 인증 코드입니다. "),
    USER_CREATE_FAIL(false, 2006, "회원가입에 실패했습니다. "),
    USER_LOGIN_FAIL(false, 2007, "로그인에 실패했습니다. "),
    USER_NOT_FOUND(false, 2008, "존재하지 않는 사용자입니다. "),
    USER_LOGINID_SUCCESS(true, 2009, "아이디가 등록되었습니다."),

    /**
     * 3000: 마이 페이지
     */
    MY_WORKSPACE_READ_SUCCESS(true, 3001, "나의 워크스페이스 목록 조회를 성공했습니다."),
    MY_MONTHLY_READ_SUCCESS(true, 3002, "나의 월간 캘린더 조회에 성공했습니다."),
    MY_WEEKLY_READ_SUCCESS(true, 3003, "나의 주간 캘린더 조회에 성공했습니다."),
    MY_DASHBOARD_READ_SUCCESS(true, 3004, "나의 대시보드 조회에 성공했습니다."),
    MY_ALARM_READ_SUCCESS(true, 3005, "나의 알람 조회에 성공했습니다."),
    MY_TASK_READ_SUCCESS(true, 3006, "나의 태스크 목록 조회를 성공했습니다."),
    MY_TASK_CHANGE_STATUS_SUCCESS(true, 3007, "나의 태스크 상태 변경을 성공했습니다."),

    /**
     * 4000: 워크스페이스
     */
    WORKSPACE_CREATE_SUCCESS(true, 4001, "워크스페이스 생성을 성공했습니다."),
    SPRINT_LABEL_CREATE_SUCCESS(true, 4002, "스프린트 라벨 생성을 성공했습니다."),
    TASK_LABEL_CREATE_SUCCESS(true, 4003, "태스크 라벨 생성을 성공했습니다."),
    SPRINT_LABEL_READ_SUCCESS(true, 4004, "스프린트 라벨 조회를 성공했습니다."),
    TASK_LABEL_READ_SUCCESS(true, 4005, "태스크 라벨 조회를 성공했습니다."),
    SPRINT_CREATE_SUCCESS(true, 4006, "스프린트 생성에 성공했습니다."),
    SPRINT_READ_SUCCESS(true, 4007, "스프린트 상세 조회에 성공했습니다."),
    SPRINT_READ_ALL_SUCCESS(true, 4008, "해당 워크스페이스의 스프린트 목록 조회에 성공했습니다."),
    TASK_CREATE_SUCCESS(true, 4009, "태스크 생성에 성공했습니다."),
    TASK_READ_SUCCESS(true, 4010, "태스크 상세 조회에 성공했습니다."),
    TASK_READ_ALL_SUCCESS(true, 4011, "해당 스프린트의 태스크 목록 조회를 성공했습니다."),
    TASK_READ_ALLWORKSPACE_SUCCESS(true, 4011, "해당 워크스페이스의 태스크 목록 조회를 성공했습니다."),
    MEETING_CREATE_SUCCESS(true, 4012, "회의 생성에 성공했습니다."),
    MEETING_READ_SUCCESS(true, 4013, "회의 상세 조회에 성공했습니다."),
    ISSUE_CREATE_SUCCESS(true, 4014, "이슈 생성에 성공했습니다."),
    TASK_STATUS_UPDATE_SUCCESS(true, 4015, "태스크 상태 변경에 성공했습니다."),
    SPRINT_STATUS_UPDATE_SUCCESS(true, 4016, "스프린트 상태 변경에 성공했습니다."),
    WORKSPACE_MONTHLY_READ_SUCCESS(true, 4017, "워크스페이스 월간 캘린더 조회에 성공했습니다."),
    WORKSPACE_WEEKLY_READ_SUCCESS(true, 4018, "워크스페이스 주간 캘린더 조회에 성공했습니다."),
    WORKSPACE_DASHBOARD_READ_SUCCESS(true, 4019, "워크스페이스 대시보드 조회에 성공했습니다. "),
    NOTE_REGISTER_SUCCESS(true,4020,"회의록 생성에 성공했습니다."),
    NOTE_SEARCH_SUCCESS(true,4021,"회의록 조회에 성공했습니다."),
    ISSUE_RETRIEVAL_SUCCESS(true, 4022, "이슈 상세 조회에 성공했습니다."),
    ISSUE_LIST_RETRIEVAL_SUCCESS(true, 4023, "이슈 리스트 조회에 성공했습니다."),
    ISSUE_NOT_FOUND(false, 4024, "이슈가 존재하지 않습니다."),
    ISSUE_WORKSPACE_MISMATCH(false, 4025, "이슈가 워스크페이스와 일치하지 않습니다."),
    ISSUE_LIST_SUCCESS(true, 4026, "이슈 리스트 조회에 조회에 성공했습니다."),
    WORKSPACE_ACCEPT_SUCCESS(true, 4027, "워크스페이스 초대를 수락했습니다."),
    WORKSPACE_DENY_SUCCESS(true, 4028, "워크스페이스 초대를 거절했습니다."),
    NOTE_LABEL_CREATE_SUCCESS(true,4029,"회의록 라벨 생성에 성공했습니다."),
    NOTE_LABEL_SEARCH_SUCCESS(true,4030,"회의록 라벨 조회에 성공했습니다."),
    TASK_READ_ALL_BY_STATUS_SUCCESS(true, 4031, "태스크 목록을 상태 별 조회에 성공했습니다."),
    NOTE_SAVE_SUCCESS(true,4032,"회의록 저장에 성공했습니다."),


    WORKSPACE_ACCESS_DENIED(false, 4101, "워크스페이스에 접근 권한이 없습니다."),
    WORKSPACE_NOT_EXISTS(false, 4102, "존재하지 않는 워크스페이스입니다."),
    LABEL_ALREADY_EXISTS(false, 4103, "동일한 라벨 이름이 존재합니다."),
    TASK_LABEL_SELECT_FAIL(false, 4104, "담당자를 지정할 권한이 존재하지 않습니다."),
    TASK_NOT_EXISTS(false, 4105, "존재하지 않는 태스크입니다."),
    MEETING_NOT_EXISTS(false, 4106, "존재하지 않는 회의 일정입니다."),
    UNCHANGED(false, 4107, "이전과 동일한 상태입니다."),
    SPRINT_NOT_EXISTS(false, 4108, "스프린트가 존재하지 않습니다."),
    WORKSPACE_INVITE_NOT_EXISTS(false, 4109, "해당 워크스페이스에 초대를 받지 않았습니다."),
    WORKSPACE_INVITE_END(false, 4110, "이미 처리 완료된 요청입니다."),
    NOTE_NOT_FOUND(false,4111,"회의록이 존재하지 않습니다."),
    /**
     * 5000: 게시판
     */
    ERRORBOARD_CREATE_SUCCESS(true,5001,"게시판 등록에 성공했습니다."),
    ERRORBOARD_SEARCH_SUCCESS(true,5101,"게시판 검색에 성공했습니다."),
    ERRORBOARD_SERACH_FAIL(true,5102,"게시판 검색에 실패했습니다."),
    ERRORCOMMENT_CREATE_SUCCESS(true,5201,"댓글 등록에 성공했습니다."),


    QABOARD_CREATE_SUCCESS(true,5001,"게시판 등록에 성공했습니다."),
    QABOARD_SEARCH_SUCCESS(true,5101,"게시판 검색에 성공했습니다."),
    QABOARD_SERACH_FAIL(true,5102,"게시판 검색에 실패했습니다."),
    QACOMMENT_CREATE_SUCCESS(true,5201,"댓글 등록에 성공했습니다."),
    QACOMMENT_SEARCH_SUCCESS(true,5202,"댓글 검색에 성공했습니다."),


    ERRORBOARD_CREATE_FAIL(false, 5002, "게시판 등록에 실패했습니다."),
    ERRORBOARD_SEARCH_FAIL(false, 5103, "게시판 검색에 실패했습니다."),
    ERRORCOMMENT_CREATE_FAIL(false, 5202, "댓글 등록에 실패했습니다."),

    QABOARD_CREATE_FAIL(false, 5003, "질문 게시판 등록에 실패했습니다."),
    QABOARD_SEARCH_FAIL(false, 5103, "질문 게시판 검색에 실패했습니다."),
    QACOMMENT_CREATE_FAIL(false, 5202, "질문 댓글 등록에 실패했습니다."),
    TASK_ALREADY_ASSIGNED(false,5203,"이미 지정된 태스크입니다."),
    /**
     * 6000: 채팅
     */

    CHATROOM_CREATE_SUCCESS(true, 6001, "채팅방 생성에 성공했습니다."),
    CHATROOM_CREATE_FAIL(false, 6002, "채팅방 생성에 실패했습니다."),

    CHATROOM_LIST_SUCCESS(true, 6101, "채팅방 조회에 성공했습니다."),
    CHATROOM_LIST_FAIL(false, 6102, "채팅방 조회에 실패했습니다."),

    MESSAGE_SEND_SUCCESS(true, 6201, "메세지가 성공적으로 전송되었습니다."),
    MESSAGE_SEND_FAIL(false, 6202, "메세지가 전송되지 않았습니다."),

    CHAT_PARTICIPATION_NOT_FOUND(false, 6203, "참여자가 존재하지 않습니다."),
    CHAT_HISTORY_RETRIEVAL_SUCCESS(true, 6301, "채팅 내역 조회에 성공했습니다."),
    CHAT_ROOM_NOT_FOUND(false, 6302, "해당 채팅방을 찾을 수 없습니다."),
    MESSAGE_HISTORY_NOT_FOUND(false, 6303, "해당 채팅방에 메시지 내역이 없습니다."),
    UNAUTHORIZED_CHAT_ACCESS(false, 6304, "해당 채팅방에 접근할 권한이 없습니다."),
    FAILED_TO_RETRIEVE_CHAT_HISTORY(false, 6305, "채팅 내역 조회에 실패했습니다."),
    CHAT_ROOM_NAME_REQUIRED(false, 6306, "채팅방 이름을 입력하지 않았습니다."),

    CHATROOM_UPDATE_SUCCESS(true, 6401, "채팅방 수정이 완료 되었습니다."),
    CHATROOM_NOT_FOUND(false, 6402, "해당 채팅방이 존재하지 않습니다."),
    CHATROOM_USER_NOT_AUTHORIZED(false, 6403, "해당 참가자는 채팅방에 존재하지 않습니다."),

    MESSAGE_DELETE_SUCCESS(true, 6501, "메세지가 성공적으로 삭제되었습니다."),

    KAFKA_SEND_FAILED(false, 6502, "카프카 메시지 전송에 실패했습니다."),
    KAFKA_RECEIVE_FAILED(false, 6503, "카프카 메시지 수신에 실패했습니다."),

    MESSAGE_CONTENT_EMPTY(false, 6504, "메시지 내용이 비어있습니다."),
    MESSAGE_NOT_FOUND(false, 6505, "메시지를 찾을 수 없습니다."),
    NOT_AUTHORIZED_TO_DELETE(false, 6506, "본인의 메세지만 삭제할 수 있습니다."),
    MESSAGE_DELETE_FAILED(false, 6507, "메세지 삭제를 실패하였습니다."),

    CHATROOM_EXIT_SUCCESS(true, 6601, "채팅방에서 성공적으로 나갔습니다."),
    CHATROOM_EXIT_FAIL(false, 6602, "채팅방 나가기에 실패했습니다."),

    MESSAGE_STATUS_UPDATE_SUCCESS(true, 6701, "메세지 상태 변경이 성공적으로 완료되었습니다."),
    MESSAGE_STATUS_UPDATE_FAIL(false, 6702, "상태 변경에 실패했습니다."),

    CHATROOM_SEARCH_SUCCESS(true, 6801, "채팅방 검색에 성공했습니다."),
    MESSAGE_UPDATE_FAILED(false, 6902, "메시지 업데이트에 실패했습니다."),
    FILE_RETRIEVAL_SUCCESS(true, 6903, "파일 내역 조회에 성공했습니다."),
    FILE_NOT_FOUND(false, 6904, "파일 내역 조회에 실패했습니다."),
    CHATBOT_MESSAGE_RECEIVED(true, 6903, "챗봇에게 메세지가 성공적으로 수신되었습니다."),
    CHATBOT_RESPONSE_SAVED(true, 6904, "챗봇 응답이 성공적으로 저장되었습니다."),
    CHATBOT_ERROR(false, 6905, "챗봇 처리 중 오류가 발생했습니다."),
    CHATBOT_USER_NOT_FOUND(false, 6906, "챗봇 메시지를 처리하는 동안 사용자를 찾을 수 없습니다."),
    CHATBOT_DATABASE_ERROR(false, 6907, "챗봇 데이터베이스 처리 중 오류가 발생했습니다."),
    CHATBOT_EXTERNAL_API_ERROR(false, 6908, "외부 API 호출 중 챗봇 오류가 발생했습니다."),
    CHATBOT_LIST_SUCCESS(true, 6909, "챗봇 대화 내역 조회가 성공했습니다."),
    CHATBOT_LIST_FAILED(false, 6910, "챗봇 대화 내역 조회에 실패했습니다."),


    /**
     * 7000: 알림
     */
    ALARM_CREATE_SUCCESS(true, 7001, "알림 전송에 성공했습니다. "),
    ALARM_CREATE_FAIL(false, 7002, "알림 전송에 실패했습니다. "),

    /**
     * 8000: 검색
     */
    SEARCH_USER_SUCCESS(true, 8001, "유저 검색에 성공했습니다. "),
    SEARCH_USER_FAIL(false, 8002, "유저 검색에 실패했습니다. ");
    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}