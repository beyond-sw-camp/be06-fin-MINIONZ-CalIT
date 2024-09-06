package minionz.backend.common.responses;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : 공통 에러
     */
    FILE_UPLOAD_FAIL(false, 1001, "파입 업로드에 실패했습니다."),



    /**
     * 2000: 유저
     */




    /**
     * 3000: 마이 페이지
     */
    MY_WORKSPACE_READ_SUCCESS(false, 3001, "내가 참여한 워크스페이스 목록 조회를 성공했습니다."),



    /**
     * 4000: 워크스페이스
     */
    WORKSPACE_CREATE_SUCCESS(false, 4001, "워크스페이스 생성을 성공했습니다."),


    WORKSPACE_ACCESS_DENIED(false, 4101, "워크스페이스에 접근 권한이 없습니다."),


    /**
     * 5000: 게시판
     */
    ERRORBOARD_CREATE_SUCCESS(true,5001,"게시글 등록에 성공했습니다."),
    ERRORBOARD_CREATE_FAIL(false,5101,"게시글 등록에 실패했습니다."),

    ERRORBOARD_SEARCH_SUCCESS(true,5201,"게시글 검색에 성공했습니다."),
    ERRORBOARD_SEARCH_FAIL(false,5202,"게시글 검색에 실패했습니다.");



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