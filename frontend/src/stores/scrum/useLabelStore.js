import { ref } from 'vue';
import { labelData } from '@/static/labelData';
import { labelColorPalette } from '@/utils/labelUtils';

export const useLabelStore = () => {
    const labelDatas = ref(labelData || []);
    const labelColorPalettes = ref(labelColorPalette || []);

    const getLabels = () => {
        return labelDatas.value;
    };

    const addLabel = (label) => {
        labelDatas.value.push(label);
    };

    const updateLabel = (index, updatedLabel) => {
        if (index >= 0 && index < labelDatas.value.length) {
            labelDatas.value[index] = updatedLabel;
        }
    };

    const deleteLabel = (index) => {
        if (index >= 0 && index < labelDatas.value.length) {
            labelDatas.value.splice(index, 1);
        }
    };

    const setLabelColor = (index, colorIndex) => {
        if (index >= 0 && index < labelDatas.value.length && colorIndex >= 0 && colorIndex < labelColorPalettes.value.length) {
            labelDatas.value[index].color = labelColorPalettes.value[colorIndex];
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