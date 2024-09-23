import { labelData as importedLabelData } from '@/static/labelData';
import { labelColorPalette as importedLabelColorPalette } from '@/utils/labelUtils';

export const useLabelStore = () => {
    const labelData = importedLabelData || [];
    const labelColorPalette = importedLabelColorPalette || [];
    const getLabels = () => {
        return labelData;
    };

    const addLabel = (label) => {
        labelData.push(label);
    };

    const updateLabel = (index, updatedLabel) => {
        if (index >= 0 && index < labelData.length) {
            labelData[index] = updatedLabel;
        }
    };

    const deleteLabel = (index) => {
        if (index >= 0 && index < labelData.length) {
            labelData.splice(index, 1);
        }
    };

    const setLabelColor = (index, colorIndex) => {
        if (index >= 0 && index < labelData.length && colorIndex >= 0 && colorIndex < labelColorPalette.length) {
            labelData[index].color = labelColorPalette[colorIndex];
        }
    };

    return {
        getLabels,
        addLabel,
        updateLabel,
        deleteLabel,
        setLabelColor
    };
};