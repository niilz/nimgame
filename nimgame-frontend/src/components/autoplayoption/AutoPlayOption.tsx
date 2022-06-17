import styles from "./AutoPlayOption.module.css";

type OptionProps = {
  onChange: (isChecked: boolean) => void;
  optionName: string;
};

export function Option(props: OptionProps) {
  return (
    <div>
      <label htmlFor={props.optionName} className={styles.Label}>
        {props.optionName}
      </label>
      <input
        name={props.optionName}
        type="checkbox"
        onChange={(e) => props.onChange(e.target.checked)}
      />
    </div>
  );
}
