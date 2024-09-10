package minionz.backend.common.responses;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : 공통 에러
     */
    FILE_UPLOAD_FAIL(false, 1001, "파입 업로드에 실패했습니다."),
    INVALID_ACCESS(false, 1002, "비정상적인 접근입니다."),


    /**
     * 2000: 유저
     */


    /**
     * 3000: 마이 페이지
     */
    MY_WORKSPACE_READ_SUCCESS(true, 3001, "나의 워크스페이스 목록 조회를 성공했습니다."),


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
    MEETING_CREATE_SUCCESS(true, 4012, "회의 생성에 성공했습니다."),
    MEETING_READ_SUCCESS(true, 4013, "회의 상세 조회에 성공했습니다."),
    ISSUE_CREATE_SUCCESS(true, 4014, "이슈 생성에 성공했습니다."),
    TASK_STATUS_UPDATE_SUCCESS(true, 4015, "태스크 상태 변경에 성공했습니다."),
    SPRINT_STATUS_UPDATE_SUCCESS(true, 4016, "스프린트 상태 변경에 성공했습니다."),
    WORKSPACE_MONTHLY_READ_SUCCESS(true, 4017, "워크스페이스 월간 캘린더 조회에 성공했습니다."),


    WORKSPACE_ACCESS_DENIED(false, 4101, "워크스페이스에 접근 권한이 없습니다."),
    WORKSPACE_NOT_EXISTS(false, 4102, "존재하지 않는 워크스페이스입니다."),
    LABEL_ALREADY_EXISTS(false, 4103, "동일한 라벨 이름이 존재합니다."),
    TASK_LABEL_SELECT_FAIL(false, 4104, "담당자를 지정할 권한이 존재하지 않습니다."),
    TASK_NOT_EXISTS(false, 4105, "존재하지 않는 태스크입니다."),
    MEETING_NOT_EXISTS(false, 4106, "존재하지 않는 회의 일정입니다."),
    UNCHANGED(false, 4107, "이전과 동일한 상태입니다."),



    /**
     * 5000: 게시판
     */
    ERRORBOARD_CREATE_SUCCESS(true, 5001, "게시판 등록에 성공했습니다."),
    ERRORBOARD_SEARCH_SUCCESS(true, 5101, "게시판 검색에 성공했습니다."),
    ERRORBOARD_SERACH_FAIL(true, 5102, "게시판 검색에 성공했습니다."),
    ERRORCOMMENT_CREATE_SUCCESS(true, 5201, "댓글 등록에 성공했습니다."),


    /**
     * 6000: 채팅
     */

    CHATROOM_CREATE_SUCCESS(true, 6001, "채팅방 생성에 성공했습니다."),
    CHATROOM_CREATE_FAIL(false, 6002, "채팅방 생성에 실패했습니다."),
    CHATROOM_LIST_SUCCESS(true, 6101, "채팅방 조회에 성공했습니다."),
    CHATROOM_LIST_FAIL(false, 6102, "채팅방 조회에 실패했습니다."),
    MESSAGE_SEND_SUCCESS(true, 6201, "메세지가 성공적으로 전송되었습니다."),
    MESSAGE_SEND_FAIL(false, 6202, "메세지가 전송되지 않았습니다."),
    CHAT_PARTICIPATION_NOT_FOUND(false, 6203, "참여자가 존재하지 않습니다.");



    /**
     * 7000: 알림
     */


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}