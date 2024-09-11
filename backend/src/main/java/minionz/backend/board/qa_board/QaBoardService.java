package minionz.backend.board.qa_board;

import minionz.backend.board.qa_board.model.AnswerStatus;
import minionz.backend.board.qa_board.model.QaBoard;
import minionz.backend.board.qa_board.model.QaBoardImage;
import minionz.backend.board.qa_board.model.request.CreateQaBoardRequest;
import minionz.backend.board.qa_board.model.response.CreateQaBoardResponse;
import minionz.backend.board.qa_board.model.response.GetQaBoardImageResponse;
import minionz.backend.board.qa_board.model.response.GetQaBoardResponse;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.workspace.WorkspaceRepository;
import minionz.backend.scrum.workspace.model.Workspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QaBoardService {
    private final QaBoardRepository qaBoardRepository;
    private final QaBoardImageRepository qaBoardImageRepository;
    private final WorkspaceRepository workspaceRepository;
    public QaBoardService(QaBoardRepository qaBoardRepository, QaBoardImageRepository qaBoardImageRepository, WorkspaceRepository workspaceRepository) {
      this.qaBoardImageRepository = qaBoardImageRepository;
      this.qaBoardRepository = qaBoardRepository;
      this.workspaceRepository = workspaceRepository;
    }

    public CreateQaBoardResponse create( List<String> fileNames, CreateQaBoardRequest request, Long workspaceId)  {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WORKSPACE_NOT_EXISTS));
        QaBoard qaBoard = QaBoard.builder()
                .qaboardTitle(request.getQaboardTitle())
                .qaboardContent(request.getQaboardContent())
                .answerStatus(AnswerStatus.NONE)
                .workSpace(workspace)
                .build();
        qaBoardRepository.save(qaBoard);
        List<GetQaBoardImageResponse> getQaBoardImageResponseList = new ArrayList<>();
        for(String fileName : fileNames){
            QaBoardImage qaBoardImage = QaBoardImage.builder()
                    .imageUrl(fileName)
                    .qaBoard(qaBoard)
                    .build();
            qaBoardImageRepository.save(qaBoardImage);
            GetQaBoardImageResponse getQaBoardImageResponse = GetQaBoardImageResponse.builder()
                    .qaBoardImageId(qaBoardImage.getQaBoardImageId())
                    .imageUrl(qaBoardImage.getImageUrl())
                    .createdAt(qaBoardImage.getCreatedAt())
                    .modifiedAt(qaBoardImage.getModifiedAt())
                    .build();
            getQaBoardImageResponseList.add(getQaBoardImageResponse);
        }
        return CreateQaBoardResponse.builder()
                .qaBoardId(qaBoard.getQaBoardId())
                .qaboardTitle(qaBoard.getQaboardTitle())
                .qaboardContent(qaBoard.getQaboardContent())
                .answerStatus(qaBoard.getAnswerStatus())
                .createdAt(qaBoard.getCreatedAt())
                .modifiedAt(qaBoard.getModifiedAt())
                .getQaBoardImageResponseList(getQaBoardImageResponseList)
                .workspaceId(qaBoard.getWorkSpace().getWorkspaceId())
                .build();

    }
    public GetQaBoardResponse read(Long boardId, Long workspaceId) throws BaseException {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WORKSPACE_NOT_EXISTS));
        QaBoard qaBoard = qaBoardRepository.findById(boardId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ERRORBOARD_SERACH_FAIL));
        List<QaBoardImage> qaBoardImageList = qaBoard.getQaBoardImageList();
        List<GetQaBoardImageResponse> getQaBoardImageResponseList = new ArrayList<>();
        for (QaBoardImage qaBoardImage : qaBoardImageList) {
            GetQaBoardImageResponse qaBoardImageResponse = GetQaBoardImageResponse.builder()
                    .qaBoardImageId(qaBoardImage.getQaBoardImageId())
                    .imageUrl(qaBoardImage.getImageUrl())
                    .createdAt(qaBoardImage.getCreatedAt())
                    .modifiedAt(qaBoardImage.getModifiedAt())
                    .build();
            getQaBoardImageResponseList.add(qaBoardImageResponse);
        }
        return GetQaBoardResponse.builder()
                .qaBoardId(qaBoard.getQaBoardId())
                .qaboardTitle(qaBoard.getQaboardTitle())
                .qaboardContent(qaBoard.getQaboardContent())
                .answerStatus(qaBoard.getAnswerStatus())
                .createdAt(qaBoard.getCreatedAt())
                .modifiedAt(qaBoard.getModifiedAt())
                .workspaceId(qaBoard.getWorkSpace().getWorkspaceId())
                .build();
    }
    public Page<GetQaBoardResponse> readAll(int page, int size, Long workspaceId)  {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WORKSPACE_NOT_EXISTS));
        Page<QaBoard> result = qaBoardRepository.findAll(PageRequest.of(page, size));
        Page<GetQaBoardResponse> getQaBoardResponses = result.map(qaBoard-> {
            List<QaBoardImage> qaBoardImages = qaBoard.getQaBoardImageList();
            List<GetQaBoardImageResponse> getQaBoardImageResponseList = new ArrayList<>();
            for (QaBoardImage qaBoardImage : qaBoardImages) {
                GetQaBoardImageResponse getQaBoardImageResponse = GetQaBoardImageResponse.builder()
                        .qaBoardImageId(qaBoardImage.getQaBoardImageId())
                        .imageUrl(qaBoardImage.getImageUrl())
                        .createdAt(qaBoardImage.getCreatedAt())
                        .modifiedAt(qaBoardImage.getModifiedAt())
                        .build();
                getQaBoardImageResponseList.add(getQaBoardImageResponse);
            }
            return GetQaBoardResponse.builder()
                    .qaBoardId(qaBoard.getQaBoardId())
                    .qaboardTitle(qaBoard.getQaboardTitle())
                    .qaboardContent(qaBoard.getQaboardContent())
                    .answerStatus(qaBoard.getAnswerStatus())
                    .createdAt(qaBoard.getCreatedAt())
                    .modifiedAt(qaBoard.getModifiedAt())
                    .getQaBoardImageResponseList(getQaBoardImageResponseList)
                    .workspaceId(qaBoard.getWorkSpace().getWorkspaceId())
                    .build();
        });
        return getQaBoardResponses;
    }
    public Page<GetQaBoardResponse> readKeyword(String keyword, int page, int size, Long workspaceId) throws BaseException {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WORKSPACE_NOT_EXISTS));
        Page<QaBoard> result = qaBoardRepository.findByKeyword(keyword, PageRequest.of(page, size))
                .orElseThrow(() -> new BaseException(BaseResponseStatus.QABOARD_SERACH_FAIL));
        Page<GetQaBoardResponse> getQaBoardResponses = result.map(qaBoard-> {
            List<QaBoardImage> qaBoardImages = qaBoard.getQaBoardImageList();
            List<GetQaBoardImageResponse> getQaBoardImageResponseList = new ArrayList<>();
            for (QaBoardImage qaBoardImage : qaBoardImages) {
                GetQaBoardImageResponse getQaBoardImageResponse = GetQaBoardImageResponse.builder()
                        .qaBoardImageId(qaBoardImage.getQaBoardImageId())
                        .imageUrl(qaBoardImage.getImageUrl())
                        .createdAt(qaBoardImage.getCreatedAt())
                        .modifiedAt(qaBoardImage.getModifiedAt())
                        .build();
                getQaBoardImageResponseList.add(getQaBoardImageResponse);
            }
            return GetQaBoardResponse.builder()
                    .qaBoardId(qaBoard.getQaBoardId())
                    .qaboardTitle(qaBoard.getQaboardTitle())
                    .qaboardContent(qaBoard.getQaboardContent())
                    .answerStatus(qaBoard.getAnswerStatus())
                    .createdAt(qaBoard.getCreatedAt())
                    .modifiedAt(qaBoard.getModifiedAt())
                    .getQaBoardImageResponseList(getQaBoardImageResponseList)
                    .workspaceId(qaBoard.getWorkSpace().getWorkspaceId())
                    .build();
        });
        return getQaBoardResponses;
    }
}
