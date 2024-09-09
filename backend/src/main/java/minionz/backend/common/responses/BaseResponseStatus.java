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
    SPRINT_READ_SUCCESS(true, 4007, "스프린트 조회에 성공했습니다."),


    WORKSPACE_ACCESS_DENIED(false, 4101, "워크스페이스에 접근 권한이 없습니다."),
    WORKSPACE_NOT_EXISTS(false, 4102, "존재하지 않는 워크스페이스입니다."),
    LABEL_ALREADY_EXISTS(false, 4103, "동일한 라벨 이름이 존재합니다."),


    /**
     * 5000: 게시판
     */
    ERRORBOARD_CREATE_SUCCESS(true,5001,"게시판 등록에 성공했습니다."),
    ERRORBOARD_SEARCH_SUCCESS(true,5101,"게시판 검색에 성공했습니다."),
    ERRORBOARD_SERACH_FAIL(true,5102,"게시판 검색에 성공했습니다."),
    ERRORCOMMENT_CREATE_SUCCESS(true,5201,"댓글 등록에 성공했습니다.");


    /**
     * 6000: 채팅
     */




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