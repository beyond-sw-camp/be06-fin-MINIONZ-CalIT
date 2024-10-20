package minionz.apiserver.board.qa_board;


import minionz.apiserver.board.qa_board.model.request.CreateQaBoardRequest;
import minionz.apiserver.board.qa_board.model.response.CreateQaBoardResponse;
import minionz.apiserver.board.qa_board.model.response.GetQaBoardImageResponse;
import minionz.apiserver.board.qa_board.model.response.GetQaBoardResponse;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.common.board.qa_board.QaBoardImageRepository;
import minionz.common.board.qa_board.QaBoardRepository;
import minionz.common.board.qa_board.model.AnswerStatus;
import minionz.common.board.qa_board.model.QaBoard;
import minionz.common.board.qa_board.model.QaBoardImage;
import minionz.common.scrum.task.repository.TaskRepository;
import minionz.common.scrum.task.model.Task;
import minionz.common.scrum.workspace.WorkspaceRepository;
import minionz.common.scrum.workspace.model.Workspace;
import minionz.common.scrum.workspace_participation.WorkspaceParticipationRepository;
import minionz.common.scrum.workspace_participation.model.WorkspaceParticipation;
import minionz.common.user.UserRepository;
import minionz.common.user.model.User;
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
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final WorkspaceParticipationRepository workspaceParticipationRepository;

    public QaBoardService(QaBoardRepository qaBoardRepository, QaBoardImageRepository qaBoardImageRepository, WorkspaceRepository workspaceRepository, UserRepository userRepository, TaskRepository taskRepository, WorkspaceParticipationRepository workspaceParticipationRepository) {
      this.qaBoardImageRepository = qaBoardImageRepository;
      this.qaBoardRepository = qaBoardRepository;
      this.workspaceRepository = workspaceRepository;
      this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.workspaceParticipationRepository = workspaceParticipationRepository;
    }

    public CreateQaBoardResponse create(List<String> fileNames, CreateQaBoardRequest request, Long workspaceId, Long userId)  {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WORKSPACE_NOT_EXISTS));
        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.TASK_NOT_EXISTS));
        WorkspaceParticipation workspaceParticipation = workspaceParticipationRepository.findById(request.getWorkspaceParticipationId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
        QaBoard qaBoard = QaBoard.builder()
                .qaboardTitle(request.getQaboardTitle())
                .qaboardContent(request.getQaboardContent())
                .answerStatus(AnswerStatus.NONE)
                .workSpace(workspace)
                .user(user1)
                .task(task)
                .workspaceParticipation(workspaceParticipation)
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
                .userName(qaBoard.getUser().getUserName())
                .taskName(qaBoard.getTask().getTaskTitle())
                .assignUser(qaBoard.getWorkspaceParticipation().getUser().getUserName())
                .build();

    }
    public GetQaBoardResponse read(Long boardId) throws BaseException {

        QaBoard qaBoard = qaBoardRepository.findById(boardId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.QABOARD_SERACH_FAIL));
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
                .userName(qaBoard.getUser().getUserName())
                .qaBoardId(qaBoard.getQaBoardId())
                .qaboardTitle(qaBoard.getQaboardTitle())
                .qaboardContent(qaBoard.getQaboardContent())
                .getQaBoardImageResponseList(getQaBoardImageResponseList)
                .answerStatus(qaBoard.getAnswerStatus())
                .createdAt(qaBoard.getCreatedAt())
                .modifiedAt(qaBoard.getModifiedAt())
                .workspaceId(qaBoard.getWorkSpace().getWorkspaceId())
                .taskName(qaBoard.getTask().getTaskTitle())
                .assignUser(qaBoard.getWorkspaceParticipation().getUser().getUserName())
                .personaImage(qaBoard.getUser().getPersona())
                .build();
    }
    public Page<GetQaBoardResponse> readAll(Long workspaceId,int page, int size)  {

        Page<QaBoard> result = qaBoardRepository.findAllByWorkspaceId(workspaceId,PageRequest.of(page, size));
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
                    .workspaceId(qaBoard.getWorkSpace().getWorkspaceId())
                    .userName(qaBoard.getUser().getUserName())
                    .qaBoardId(qaBoard.getQaBoardId())
                    .qaboardTitle(qaBoard.getQaboardTitle())
                    .qaboardContent(qaBoard.getQaboardContent())
                    .answerStatus(qaBoard.getAnswerStatus())
                    .createdAt(qaBoard.getCreatedAt())
                    .modifiedAt(qaBoard.getModifiedAt())
                    .getQaBoardImageResponseList(getQaBoardImageResponseList)
                    .workspaceId(qaBoard.getWorkSpace().getWorkspaceId())
                    .taskName(qaBoard.getTask().getTaskTitle())
                    .assignUser(qaBoard.getWorkspaceParticipation().getUser().getUserName())
                    .personaImage(qaBoard.getUser().getPersona())
                    .build();
        });
        return getQaBoardResponses;
    }
    public Page<GetQaBoardResponse> readKeyword(Long workspaceId,String keyword, int page, int size) throws BaseException {

        Page<QaBoard> result = qaBoardRepository.findByKeyword(keyword,workspaceId, PageRequest.of(page, size))
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
                    .userName(qaBoard.getUser().getUserName())
                    .qaBoardId(qaBoard.getQaBoardId())
                    .qaboardTitle(qaBoard.getQaboardTitle())
                    .qaboardContent(qaBoard.getQaboardContent())
                    .answerStatus(qaBoard.getAnswerStatus())
                    .createdAt(qaBoard.getCreatedAt())
                    .modifiedAt(qaBoard.getModifiedAt())
                    .getQaBoardImageResponseList(getQaBoardImageResponseList)
                    .workspaceId(qaBoard.getWorkSpace().getWorkspaceId())
                    .taskName(qaBoard.getTask().getTaskTitle())
                    .assignUser(qaBoard.getWorkspaceParticipation().getUser().getUserName())
                    .personaImage(qaBoard.getUser().getPersona())
                    .build();
        });
        return getQaBoardResponses;
    }
}
